package com.unir.products.service;

import java.math.BigDecimal;
import java.util.List;

import com.unir.products.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.products.data.DataAccessRepository;
import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

@Service
public class ProductsServiceImpl implements ProductsService {


	@Autowired
	private ProductRepository repository;

	@Override
	public List<Product> getProducts() {

		List<Product> products = repository.findAll();
		return products.isEmpty() ? null : products;
	}

	@Override
	public Product getProduct(Integer productoid) {
		return repository.findById(String.valueOf(productoid)).orElse(null);
	}




	@Override
	public Boolean removeProduct(Integer productId) {

		Product product = repository.findById(String.valueOf(productId)).orElse(null);

		if (product != null) {
			repository.delete(product);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Product createProduct(CreateProductRequest request) {

		if (request != null && StringUtils.hasLength(request.getDescripcion().trim())
				&& StringUtils.hasLength(String.valueOf(request.getProductoid()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getCategoriaid()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getStock()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getNombre()).trim()) && request.getPreciounit() != null) {

			Product product = Product.builder().descripcion(request.getDescripcion()).stock(Integer.valueOf(request.getStock())).nombre(String.valueOf (request.getNombre()))
					.categoriaid(Integer.valueOf(request.getCategoriaid())).visible(Boolean.valueOf(request.getVisible())).preciounit(Double.valueOf(request.getPreciounit())).productoid(Integer.valueOf(request.getProductoid())).build();

			return repository.save(product);
		} else {
			return null;
		}
	}

	@Override
	public Product updateProduct(CreateProductRequest request) {
		if (request != null && StringUtils.hasLength(request.getDescripcion().trim())
				&& StringUtils.hasLength(String.valueOf(request.getProductoid()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getCategoriaid()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getStock()).trim())
				&& StringUtils.hasLength(String.valueOf(request.getVisible()).trim()) && request.getPreciounit() != null) {

			Product product = Product.builder().descripcion(request.getDescripcion()).stock(Integer.valueOf(request.getStock()))
					.categoriaid(Integer.valueOf(request.getCategoriaid())).visible(Boolean.valueOf(request.getVisible())).preciounit(Double.valueOf(request.getPreciounit())).productoid(Integer.valueOf(request.getProductoid())).build();

			return repository.save(product);
		} else {
			return null;
		}
	}


}
