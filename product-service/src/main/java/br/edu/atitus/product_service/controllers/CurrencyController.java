package br.edu.atitus.product_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.product_service.clients.CurrencyResponse;
import br.edu.atitus.product_service.services.CurrencyService;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/product/currency/{value}/{source}/{target}")
    public CurrencyResponse convertCurrency(
            @PathVariable double value,
            @PathVariable String source,
            @PathVariable String target) {
        return currencyService.getCurrency(value, source, target);
    }
}
