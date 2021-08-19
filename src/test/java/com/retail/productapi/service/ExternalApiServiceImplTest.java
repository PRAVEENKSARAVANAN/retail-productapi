package com.retail.productapi.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExternalApiServiceImplTest {


    private ExternalApiService externalApiService;
    private RestTemplate restTemplate;

    private String url = "";
    private String apiResponse ="";

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        externalApiService = new ExternalApiServiceImpl(restTemplate);

        apiResponse = "{\n" +
                "product: {\n" +
                "item: {\n" +
                "product_description: {\n" +
                "title: \"The Big Lebowski (Blu-ray)\",\n" +
                "}}}}";

        url = "redskyUrl";
    }



    @Test
    void getExternalApiResponseThrowHttpClientErrorException() throws ExecutionException, InterruptedException {
        //setup
        when(restTemplate.getForObject(url, String.class)).thenReturn(apiResponse);

        //test
        CompletableFuture<String> completableFuture  = externalApiService.getExternalApiResponse(url, String.class);
        String productApiResponseString = (String)completableFuture.get();
        String productName = JsonPath.read(productApiResponseString, "$.product.item.product_description.title");
        Assertions.assertEquals(productName, "The Big Lebowski (Blu-ray)");
    }
}