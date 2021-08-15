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

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private PriceService priceService;

    @Value("${redsky.domain.url}")
    private String redskyDomainUrl;

    @Value("${redsky.pdp.path.url}")
    private String pdpPathUrl;

    @Value("${redsky.pdp.url.params}")
    private String pdpQueryParams;

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
            Price priceResponse = priceService.updatePrice(productId, new Price(product.getPrice().getPrice(), product.getPrice().getCurrencyCode()));
            productResponse.setPrice(priceResponse);
        }
        return productResponse;
    }

    public Product getProduct(Long productId) {
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
