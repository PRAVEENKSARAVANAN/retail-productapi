package com.retail.productapi.model;

import lombok.Data;

@Data
public class Product {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
