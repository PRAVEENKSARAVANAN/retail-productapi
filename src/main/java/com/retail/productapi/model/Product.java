package com.retail.productapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {

    private Long id;

    private String name;

    @JsonProperty("current_price")
    private Price price;

}
