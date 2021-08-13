package com.retail.productapi.service;

import com.jayway.jsonpath.JsonPath;
import com.retail.productapi.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiServiceImpl<T> implements ExternalApiService {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    @Override
    public CompletableFuture getExternalApiResponse(String url, Class responseType) {

        String productApiResponse = null;
        try {
            productApiResponse = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                logger.info("External Api Error, Resource not found");
            } else {
                logger.info("External Api Error = {}. Retrying, url = {}", ex, url);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "External API call failed.");
            }
        } catch (Exception ex) {
            logger.error("External API throws exception, url={}, error={}", url, ex.getMessage());
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "External API call failed.");
        }
        return CompletableFuture.completedFuture(productApiResponse);
    }
}
