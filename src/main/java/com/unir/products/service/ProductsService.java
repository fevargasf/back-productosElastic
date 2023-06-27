package com.unir.products.service;

import java.util.List;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

public interface ProductsService {

	List<Product> getProducts();

	Product getProduct(Integer productoid);

	Boolean removeProduct(Integer productoid);

	Product createProduct(CreateProductRequest request);

	Product updateProduct(CreateProductRequest request);


}
