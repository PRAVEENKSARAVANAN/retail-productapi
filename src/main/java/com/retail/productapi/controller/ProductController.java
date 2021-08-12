package com.retail.productapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(value = "/api/v1", produces = "application/json", consumes = "application/json")
public class ProductController {


    @GetMapping(path = "/products/{productId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Product Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ApiOperation(value = "Retrieve Product and Price details by Product Id")
    ResponseEntity getProductInfo(@PathVariable String productId  ){
        ResponseEntity responseEntity = null;

        return responseEntity;
    }
}
