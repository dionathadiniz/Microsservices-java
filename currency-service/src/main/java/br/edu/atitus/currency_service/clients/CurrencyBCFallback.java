package br.edu.atitus.currency_service.clients;

import java.util.Collections;

import org.springframework.stereotype.Component;

@Component
public class CurrencyBCFallback implements CurrencyBCClient {

    @Override
    public CurrencyBCResponse getDollar(String dataCotacao) {
        CurrencyBCResponse fallback = new CurrencyBCResponse();
        fallback.setValue(Collections.emptyList()); 
        return fallback;
    }
}
