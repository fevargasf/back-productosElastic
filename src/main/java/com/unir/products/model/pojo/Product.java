package com.unir.products.model.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "products", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

	@Id
	private Integer productoid;
	
	@Field(type = FieldType.Text, name = "nombre")
	private String nombre;
	
	@Field(type = FieldType.Integer, name = "categoriaid")
	private Integer categoriaid ;
	
	@Field(type = FieldType.Search_As_You_Type, name = "descripcion")
	private String descripcion;

    @Field(type = FieldType.Integer, name = "stock")
    private Integer stock ;

    @Field(type = FieldType.Double, name = "preciounit")
    private Double preciounit ;

	@Field(type = FieldType.Boolean, name = "visible")
	private Boolean visible;


}
