package com.example.products.service;

import com.example.products.rest.CreateProductRestModel;

public interface ProductService {

    String createProduct(CreateProductRestModel productRestModel);

    String createProductSynchronous(CreateProductRestModel productRestModel) throws Exception;
}
