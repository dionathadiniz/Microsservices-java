package br.edu.atitus.currency_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String target;
    private double conversionRate;
    private double convertedValue;
    private String enviroment;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public double getConversionRate() { return conversionRate; }
    public void setConversionRate(double conversionRate) { this.conversionRate = conversionRate; }

    public double getConvertedValue() { return convertedValue; }
    public void setConvertedValue(double convertedValue) { this.convertedValue = convertedValue; }

    public String getEnviroment() { return enviroment; }
    public void setEnviroment(String enviroment) { this.enviroment = enviroment; }
}
