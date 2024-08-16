package com.example.product.service;

import java.util.List;

import com.example.product.model.Product;

public interface ProductService 
{

	void saveProduct(Product product);
	//Product getProductById(Long id);
	void updateProduct(Long id,Product product);
	void deleteProduct(Long id);
	List<Product> getAllProducts();
	//List<Product> getProducts(int page, int size);
	int getTotalProductCount();
	List<Product> getProducts(int page, int size);
	
	
	
}

