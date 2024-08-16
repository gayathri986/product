package com.example.product.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.model.Product;
import com.example.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController 
{

	private final ProductService productService;

	public ProductController(ProductService productService)
	{
		this.productService=productService;
	}

	@PostMapping(path="/product")
	public void createProduct(@RequestBody Product product)
	{
		productService.saveProduct(product);

	}

	@PutMapping(path="/{id}")
	public void updateProdct(@PathVariable Long id,@RequestBody Product product)
	{
		productService.updateProduct(id, product);
	}

	@DeleteMapping(path="/{id}")
	public void deleteProduct(@PathVariable Long id)
	{
		productService.deleteProduct(id);
	}
	@GetMapping(path="/allproducts")
	public List<Product> getAllProducts() 
	{
		return productService.getAllProducts();
	}

	@GetMapping()
	public List<Product> getProducts(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size
			) {
		// Validate page and size
		if (page < 1) {
			throw new IllegalArgumentException("Page number must be greater than 0");
		}
		if (size < 1) {
			throw new IllegalArgumentException("Page size must be greater than 0");
		}
		return productService.getProducts(page, size);
	}

	@GetMapping(path="/count")
	public int getProductCount() {
		return productService.getTotalProductCount();
	}
}
