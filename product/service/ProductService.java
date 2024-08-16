package com.example.product.service;

import java.util.List;

import com.example.product.model.Product;

public interface ProductService 
{

	void saveProduct(Product product);
	void updateProduct(Long id,Product product);
	void deleteProduct(Long id);
	List<Product> getAllProducts();
	int getTotalProductCount();
	List<Product> getProducts(int page, int size);
	
}

