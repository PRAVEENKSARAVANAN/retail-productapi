package com.retail.productapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.productapi.model.Price;
import com.retail.productapi.model.Product;
import com.retail.productapi.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@WebMvcTest(ProductController.class)
class ProductControllerTest {



    @Autowired
    private MockMvc mockMvc;

    private Product product;
    private Price price;
    private ProductService productService;
    private PriceService priceService;
    @Autowired
    private ProductController productController;

    private ExternalApiService externalApiService;
    private String apiResponse = null;


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    //@Test
    void shouldReturnProductWithPrice() throws Exception {
        Long productId = Long.valueOf(13860428);
        Product product = new Product();
        product.setId(productId);
        product.setName("The Big Lebowski (Blu-ray) (Widescreen)");
        Price price = new Price(13.49, "USD");
        product.setPrice(price);

        when(productService.getProductWithPrice(Long.valueOf(13860428))).
                thenReturn(product);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/"+productId.longValue())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Product[] productlist = mapFromJson(content, Product[].class);
        assertTrue(productlist.length > 0);

    }

    @BeforeEach
    void setUp() {


        //priceService = mock(PriceServiceImpl.class);
        //externalApiService = mock(ExternalApiServiceImpl.class);

        //productService = new ProductServiceImpl(externalApiService, priceService,
                //"redskyUrl", "pdpUrl", "queryParam");
        productService = mock(ProductService.class);
        // Price Object
        price = new Price();
        price.setPrice(12);
        price.setCurrencyCode("USD");

        // Product Data
        product = new Product();
        product.setId(Long.valueOf("1234"));
        product.setPrice(price);

        productController = new ProductController(productService);
    }

    @Test
    void getProductWithPrice() {
        // setuo

        when(productService.getProductWithPrice(Long.valueOf("1234"))).thenReturn(product);

        ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(product, HttpStatus.OK);

        //test
        ResponseEntity<Product> productResponseEntity = productController.getProductWithPrice(Long.valueOf("1234"));
        Assertions.assertEquals(expectedResponseEntity.getStatusCode(), productResponseEntity.getStatusCode());
    }

    @Test
    void getProductWithPriceForInvalidProductId() {
        // setuo
        when(productService.getProductWithPrice(Long.valueOf("12345"))).thenReturn(null);
        ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        //test
        ResponseEntity<Product> productResponseEntity = productController.getProductWithPrice(Long.valueOf("12345"));
        Assertions.assertEquals(expectedResponseEntity.getStatusCode(), productResponseEntity.getStatusCode());
    }

    @Test
    void updatePriceForProduct() {
        // setuo
        when(productService.updatePriceByProductId(Long.valueOf("1234"), product)).thenReturn(product);
        ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(product, HttpStatus.OK);

        //test
        ResponseEntity<Product> productResponseEntity = productController.updatePriceForProduct(Long.valueOf("1234"),product);
        Assertions.assertEquals(expectedResponseEntity.getStatusCode(), productResponseEntity.getStatusCode());

    }

    @Test
    void updatePriceForInvalidProduct() {
        // setuo
        when(productService.updatePriceByProductId(Long.valueOf("12345"), product)).thenReturn(null);
        ResponseEntity<Product> expectedResponseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        //test
        ResponseEntity<Product> productResponseEntity = productController.updatePriceForProduct(Long.valueOf("1234"),product);
        Assertions.assertEquals(expectedResponseEntity.getStatusCode(), productResponseEntity.getStatusCode());
    }
}