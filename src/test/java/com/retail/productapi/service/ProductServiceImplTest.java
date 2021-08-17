package com.retail.productapi.service;

import com.retail.productapi.model.Price;
import com.retail.productapi.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductService productService;
    private PriceService priceService;
    private ExternalApiService externalApiService;
    private String apiResponse = null;
    private Product product;
    private Price price;


    @BeforeEach
    void setup(){
        priceService = mock(PriceServiceImpl.class);
        externalApiService = mock(ExternalApiServiceImpl.class);
        productService = new ProductServiceImpl(externalApiService, priceService,
                "redskyUrl", "pdpUrl", "queryParam");
        apiResponse = "{\n" +
                "product: {\n" +
                "item: {\n" +
                "product_description: {\n" +
                "title: \"The Big Lebowski (Blu-ray)\",\n" +
                "}}}}";

        // Price Object
        price = new Price();
        price.setPrice(12);
        price.setCurrencyCode("USD");

        // Product Data
        product = new Product();
        product.setId(Long.valueOf("1234"));
        product.setPrice(price);
    }

    @Test
    void getProductWithPrice() {

        CompletableFuture<String> apiResponse = CompletableFuture.completedFuture(this.apiResponse);
        when(externalApiService.getExternalApiResponse("redskyUrlpdpUrl" + Long.valueOf(1234) + "queryParam", String.class)).thenReturn(apiResponse);
        when(priceService.getPrice(Long.valueOf(1234))).thenReturn(price);

        //test
        Product result = productService.getProductWithPrice(Long.valueOf(1234));

        Assertions.assertEquals(result.getName(), "The Big Lebowski (Blu-ray)");
    }

    @Test
    void updatePriceByProductId() {

        CompletableFuture<String> apiResponse = CompletableFuture.completedFuture(this.apiResponse);
        when(externalApiService.getExternalApiResponse("redskyUrlpdpUrl" + Long.valueOf(1234) + "queryParam", String.class)).thenReturn(apiResponse);
        when(priceService.updatePrice(Long.valueOf(1234), price)).thenReturn(price);

        //test
        Product productResponse = productService.updatePriceByProductId(Long.valueOf("1234"), product);

        Assertions.assertEquals(productResponse.getPrice().getPrice(), Double.valueOf(12));
    }

}