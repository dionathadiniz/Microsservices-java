package br.edu.atitus.currency_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CurrencyBCClient",
             url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata",
			fallback = CurrencyBCFallback.class)
public interface CurrencyBCClient {

    @GetMapping("/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='{dataCotacao}'&$format=json")
    CurrencyBCResponse getDollar(@PathVariable("dataCotacao") String dataCotacao);
}
