package com.retail.productapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Model Object for the Price
 */
@Data
public class Price {

    @JsonProperty("value")
    private double price;

    @JsonProperty("currency_code")
    private String currencyCode;

    public Price() {
        super();
    }

    public Price(double price, String currencyCode) {
        this.price = price;
        this.currencyCode = currencyCode;
    }
}
