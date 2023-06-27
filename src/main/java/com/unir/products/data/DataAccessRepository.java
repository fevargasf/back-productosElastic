package com.unir.products.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.unir.products.model.pojo.Product;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

	// Esta clase (y bean) es la unica que usan directamente los servicios para
	// acceder a los datos.
	private final ProductRepository productRepository;
	private final ElasticsearchOperations elasticClient;

	private final String[] descriptionSearchFields = { "descripcion", "descripcion._2gram", "descripcion._3gram" };

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Boolean delete(Product product) {
		productRepository.delete(product);
		return Boolean.TRUE;
	}

	public List<Product> findProducts(String nombre, Double categoriaid, String descripcion, Integer stock, Double preciounit,Boolean aggregate) {

		BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

		if (!StringUtils.isEmpty(String.valueOf(nombre))) {
			querySpec.must(QueryBuilders.termsQuery("nombre", nombre));
		}



		if (preciounit  != null) {
			querySpec.must(QueryBuilders.rangeQuery("preciounit"));
		}





		if (!StringUtils.isEmpty(descripcion)) {
			querySpec.must(QueryBuilders.multiMatchQuery(descripcion, descriptionSearchFields).type(Type.BOOL_PREFIX));
		}

		//Si no he recibido ningun parametro, busco todos los elementos.
		if (!querySpec.hasClauses()) {
			querySpec.must(QueryBuilders.matchAllQuery());
		}
		
		//Filtro implicito
		//No le pido al usuario que lo introduzca pero lo aplicamos proactivamente en todas las peticiones
		//En este caso, que los productos sean visibles (estado correcto de la entidad)
		querySpec.must(QueryBuilders.termQuery("visible", true));

		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);
		
		if(aggregate.booleanValue()) {
			nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("Nombre Aggregation").field("nombre"));
		}
		
		Query query = nativeSearchQueryBuilder.build();
		SearchHits<Product> result = elasticClient.search(query, Product.class);
		
		if(result.hasAggregations()) {
			
			Map<String, Aggregation> aggs = result.getAggregations().asMap();
			ParsedStringTerms countryAgg = (ParsedStringTerms) aggs.get("Country Aggregation");
			countryAgg.getBuckets().forEach(bucket -> log.info("Bucket {} tiene {} elementos", bucket.getKey(), bucket.getDocCount()));
		}
		return result.getSearchHits().stream().map(SearchHit::getContent).toList();
	}

	public Optional<Product> findByIdProductoid(Integer productoid) {
		return productRepository.findByProductoid(productoid);
	}
}
