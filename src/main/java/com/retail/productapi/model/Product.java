package com.retail.productapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Model Object for the Product
 *
 */
@Data
public class Product {

    private Long id;

    private String name;

    @JsonProperty("current_price")
    private Price price;

}
