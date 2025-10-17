package br.edu.atitus.currency_service.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.currency_service.clients.CurrencyBCClient;
import br.edu.atitus.currency_service.clients.CurrencyBCResponse;
import br.edu.atitus.currency_service.entities.CurrencyEntity;
import br.edu.atitus.currency_service.repositories.CurrencyRepository;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private final CurrencyRepository repository;
    private final CurrencyBCClient currencyBCClient;
    private final CacheManager cacheManager;

    @Value("${server.port}")
    private Integer serverPort;

    public CurrencyController(CurrencyRepository repository, CurrencyBCClient currencyBCClient, CacheManager cacheManager) {
        this.repository = repository;
        this.currencyBCClient = currencyBCClient;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/{value}/{source}/{target}")
    public ResponseEntity<CurrencyEntity> getConversion(
            @PathVariable double value,
            @PathVariable String source,
            @PathVariable String target) throws Exception {

        source = source.toUpperCase();
        target = target.toUpperCase();

        String nameCache = "Currency";
        String keyCache = source + target;

       
        Cache cache = cacheManager.getCache(nameCache);
        CurrencyEntity currency = null;
        if (cache != null) {
            currency = cache.get(keyCache, CurrencyEntity.class);
        }

        if (currency != null) {
            currency.setConvertedValue(value * currency.getConversionRate());
            currency.setEnviroment("Currency-service running on port: " + serverPort + " - DataSource: Cache");
            return ResponseEntity.ok(currency);
        }

        
        currency = new CurrencyEntity();
        currency.setSource(source);
        currency.setTarget(target);

        double conversionRate = 1.0;
        String dataSource = "None";

        if (!source.equals(target)) {
            String date = LocalDate.now().minusDays(1)
                    .format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

            try {
                if (source.equals("USD") && target.equals("BRL")) {
                    CurrencyBCResponse resp = currencyBCClient.getDollar(date);

                    if (resp == null || resp.getValue() == null || resp.getValue().isEmpty()) {
                        throw new Exception("Cotação não encontrada para a data: " + date);
                    }

                    conversionRate = resp.getValue().get(0).getCotacaoVenda();
                    dataSource = "API BCB PTAX";

                } else if (source.equals("BRL") && target.equals("USD")) {
                    CurrencyBCResponse resp = currencyBCClient.getDollar(date);

                    if (resp == null || resp.getValue() == null || resp.getValue().isEmpty()) {
                        throw new Exception("Cotação não encontrada para a data: " + date);
                    }

                    conversionRate = 1 / resp.getValue().get(0).getCotacaoVenda();
                    dataSource = "API BCB PTAX";

                } else {
                    throw new Exception("Moeda não suportada. Apenas BRL ↔ USD");
                }

            } catch (Exception e) {
                
                currency = repository.findBySourceAndTarget(source, target)
                        .orElseThrow(() -> new Exception("Currency Unsupported"));
                conversionRate = currency.getConversionRate();
                dataSource = "Local Database";
            }
        }


        currency.setConversionRate(conversionRate);
        currency.setConvertedValue(value * conversionRate);
        currency.setEnviroment("Currency-service running on port: " + serverPort + " - DataSource: " + dataSource);

       
        if (cache != null) {
            cache.put(keyCache, currency);
        }

        return ResponseEntity.ok(currency);
    }
}
