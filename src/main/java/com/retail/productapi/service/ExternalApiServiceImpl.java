package com.retail.productapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * Service Implementation class for External APIs
 *
 * @param <T>
 */
@Service
public class ExternalApiServiceImpl<T> implements ExternalApiService {

    RestTemplate restTemplate ;

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    @Autowired
    public ExternalApiServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     *
     * This Method is used to invoke external API, based on the URL.
     *
     * @param url
     * @param responseType
     * @return CompletableFuture
     */
    @Override
    public CompletableFuture getExternalApiResponse(String url, Class responseType) {

        String productApiResponse = null;
        try {
            // Use RestTemplate to fetch the response from the API
            productApiResponse = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                logger.info("External Api Error = {}. Retrying, url = {}", ex, url);
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
