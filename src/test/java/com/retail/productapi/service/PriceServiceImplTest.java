package com.retail.productapi.service;

import com.retail.productapi.dao.PriceDao;
import com.retail.productapi.entity.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceServiceImplTest {

    private PriceService priceService;

    private PriceDao priceDao;

    private Price priceEntity;

    private com.retail.productapi.model.Price priceModel;

    @BeforeEach
    void setUp() {
        priceDao = mock(PriceDao.class);
        priceService = new PriceServiceImpl(priceDao);
        priceEntity = new Price(Long.valueOf("1234"), Double.valueOf(12), "USD");
        priceModel = new com.retail.productapi.model.Price(Double.valueOf(12), "USD");

    }

    @Test
    void getPrice() {
        when(priceDao.findById(Long.valueOf("1234"))).thenReturn(Optional.of(priceEntity));

        // Test
        com.retail.productapi.model.Price priceResponse = priceService.getPrice(Long.valueOf("1234"));

        Assertions.assertEquals(priceEntity.getPrice(), priceResponse.getPrice());
    }

    @Test
    void getPriceForInvalidProduct() {
        when(priceDao.findById(Long.valueOf("12345"))).thenReturn(Optional.empty());

        // Test
        com.retail.productapi.model.Price priceResponse = priceService.getPrice(Long.valueOf("12345"));

        Assertions.assertEquals(null,priceResponse);
    }

    @Test
    void updatePrice() {

        when(priceDao.save(priceEntity)).thenReturn(priceEntity);

        // Test
        com.retail.productapi.model.Price priceResponse = priceService.updatePrice(Long.valueOf("1234"), priceModel);

        Assertions.assertEquals(priceModel,priceResponse);

    }
}