package com.retail.productapi.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "productPrice")
@Data
public class Price {

    @PrimaryKey(value="id")
    private Long id;

    @Column(value = "price")
    private Double price;

    @Column(value = "currencyCode")
    private String currencyCode;

    public Price(){
        super();
    }

    public Price(Long id, Double price, String currencyCode) {
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
    }

}
