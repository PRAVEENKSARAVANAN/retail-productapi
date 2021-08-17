package com.retail.productapi.service;

import com.jayway.jsonpath.JsonPath;
import com.retail.productapi.model.Price;
import com.retail.productapi.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private ExternalApiService externalApiService;

    private PriceService priceService;

    private String redskyDomainUrl;

    private String pdpPathUrl;

    private String pdpQueryParams;

    @Autowired
    public ProductServiceImpl(ExternalApiService externalApiService, PriceService priceService,
                              @Value("${redsky.domain.url}") String redskyDomainUrl,
                              @Value("${redsky.pdp.path.url}") String pdpPathUrl,
                              @Value("${redsky.pdp.url.params}") String pdpQueryParams) {
        this.externalApiService = externalApiService;
        this.priceService = priceService;
        this.redskyDomainUrl = redskyDomainUrl;
        this.pdpPathUrl = pdpPathUrl;
        this.pdpQueryParams = pdpQueryParams;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product getProductWithPrice(Long productId) {
        Product productResponse = getProduct(productId);
        if (productResponse != null)
            productResponse.setPrice(priceService.getPrice(productId));
        return productResponse;
    }

    @Override
    public Product updatePriceByProductId(Long productId, Product product) {
        Product productResponse = getProduct(productId);
        if (productResponse != null) {
            Price priceResponse = priceService.updatePrice(productId, product.getPrice());
            productResponse.setPrice(priceResponse);
        }
        return productResponse;
    }

    private Product getProduct(Long productId) {
        CompletableFuture<String> completableFuture = externalApiService.getExternalApiResponse(redskyDomainUrl + pdpPathUrl + productId.toString() + pdpQueryParams, String.class);
        String productApiResponse = null;
        try {
            productApiResponse = (String) completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Redsky Product PDP API Exception : " + e.getMessage());
        }

        Product productResponse = null;
        if (productApiResponse != null) {
            String productName = JsonPath.read(productApiResponse, "$.product.item.product_description.title");
            productResponse = new Product();
            productResponse.setId(productId);
            productResponse.setName(productName);
        }

        return productResponse;
    }


}
