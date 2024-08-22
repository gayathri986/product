package com.example.product.service;

import java.util.List;
import java.util.Map;

import com.example.product.model.Product;

public interface ProductService 
{

	Map<String, Object> saveProduct(Product product);
	Map<String, Object> updateProduct(String id,Product product);
	Map<String, Object> deleteProduct(String id);
	List<Map<String, Object>> getAllProducts();
	Map<String, Object> getdetailsById(String id);
    int getTotalProductCount();
}

