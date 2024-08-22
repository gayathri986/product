package com.example.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Map<String, Object> saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Map<String,Object> updateProduct(String id, Product product) {
		product.setProductId(id);
		return productRepository.update(product);
	}

	@Override
	public Map<String, Object> deleteProduct(String id) {
		return productRepository.delete(id);
	}

	@Override
	public List<Map<String, Object>> getAllProducts() {
		return productRepository.allproducts();
	}

	@Override 
	public Map<String,Object> getdetailsById(String id)
	{
		return productRepository.getbyId(id);
	}

	public int getTotalProductCount() {
		return productRepository.countProducts();
	}
}