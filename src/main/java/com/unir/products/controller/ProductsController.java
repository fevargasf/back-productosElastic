package com.unir.products.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.service.ProductsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

	private final ProductsService service;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(
			@RequestHeader Map<String, String> headers,
			@RequestParam(required = false) String descripcion,
			@RequestParam(required = false) String nombre,
			@RequestParam(required = false) Integer categoriaid,
//			@RequestParam(required = false) Integer stock,
			@RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

		log.info("headers: {}", headers);
		List<Product> products = service.getProducts();

		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}

	@GetMapping("/products/available")
	public ResponseEntity<Boolean> getProduct(@RequestParam Integer productoId,
											  @RequestParam Integer cantidad) {

		log.info("Request received for product {}", productoId);
		Product product = service.getProduct(productoId);

		if (product != null && product.getStock() >= cantidad) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}

	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {

		Boolean removed = service.removeProduct(productId);

		if (Boolean.TRUE.equals(removed)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/products")
	public ResponseEntity<Product> getProduct(@RequestBody CreateProductRequest request) {

		Product createdProduct = service.createProduct(request);

		if (createdProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}


	@PutMapping("/products")
	public ResponseEntity<Product> updProduct(@RequestBody CreateProductRequest request)
	{
		Product updateProduct = service.updateProduct(request);
		if (updateProduct != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

}
