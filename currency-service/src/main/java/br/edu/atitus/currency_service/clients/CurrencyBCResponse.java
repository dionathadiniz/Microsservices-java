package br.edu.atitus.currency_service.clients;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyBCResponse {

    @JsonProperty("value")
    private List<Cotacao> value;

    public List<Cotacao> getValue() {
        return value;
    }

    public void setValue(List<Cotacao> value) {
        this.value = value;
    }

    public static class Cotacao {
        @JsonProperty("cotacaoVenda")
        private double cotacaoVenda;

        public double getCotacaoVenda() {
            return cotacaoVenda;
        }

        public void setCotacaoVenda(double cotacaoVenda) {
            this.cotacaoVenda = cotacaoVenda;
        }
    }
}
