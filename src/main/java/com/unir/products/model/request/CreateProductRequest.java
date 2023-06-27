package com.unir.products.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	private String nombre;
	private Integer productoid;
	private Integer categoriaid;
	private Integer stock;
	private Double preciounit;
	private String descripcion;
	private Boolean visible;
}
