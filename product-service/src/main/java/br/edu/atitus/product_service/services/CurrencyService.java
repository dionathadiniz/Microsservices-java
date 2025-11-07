package br.edu.atitus.product_service.services;

import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import br.edu.atitus.product_service.clients.CurrencyClient;
import br.edu.atitus.product_service.clients.CurrencyResponse;

@Service
public class CurrencyService {

    private final CurrencyClient currencyClient;

    public CurrencyService(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

  
    @CircuitBreaker(name = "currencyService", fallbackMethod = "fallbackGetCurrency")
    @Retry(name = "currencyService")
    public CurrencyResponse getCurrency(double value, String source, String target) {
        return currencyClient.getCurrency(value, source, target);
    }

   
    public CurrencyResponse fallbackGetCurrency(double value, String source, String target, Throwable throwable) {
        CurrencyResponse response = new CurrencyResponse();
        response.setSource(source);
        response.setTarget(target);
        response.setConvertedValue(0.0);
        response.setEnviroment("Fallback: serviço de conversão indisponível");
        return response;
    }
}
