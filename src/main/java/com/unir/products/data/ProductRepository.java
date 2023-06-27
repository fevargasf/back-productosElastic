package com.unir.products.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.unir.products.model.pojo.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

	List<Product> findAllByNombre(String nombre);
	
	Optional<Product> findByProductoid(Integer productoid);
	
	Product save(Product product);
	
	void delete(Product product);
	
	List<Product> findAll();
}
