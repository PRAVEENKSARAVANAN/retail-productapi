package com.retail.productapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Price {

    @JsonProperty("value")
    private double price;

    @JsonProperty("currency_code")
    private String currencyCode;

    public Price(double price, String currencyCode) {
        this.price = price;
        this.currencyCode = currencyCode;
    }
}
