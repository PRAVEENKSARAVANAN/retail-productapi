package com.retail.productapi.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ExternalApiService<T> {

    @Async
    CompletableFuture<T> getExternalApiResponse(String url, Class<T> responseType);
}
