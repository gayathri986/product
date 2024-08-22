package com.example.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
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
	@PostMapping(path="/addproduct")
	public Map<String, Object> addProduct(@RequestBody Product product) 
	{
		return productService.saveProduct(product);
	}

	@PutMapping(path="/{id}")
	public Map<String, Object> updateProduct(@PathVariable String id,@RequestBody Product product)
	{
		return productService.updateProduct(id, product);

	}

	@DeleteMapping(path="/{id}")
	public Map<String, Object> deleteProduct(@PathVariable String id)
	{
		return productService.deleteProduct(id);

	}

	@GetMapping(path="/allproducts")
	public List<Map<String, Object>> getAllProducts() 
	{
		return productService.getAllProducts();
	}

	@GetMapping(path="/{id}")
	public Map<String,Object> getdetailsById(@PathVariable String id)
	{
		return productService.getdetailsById(id);
	}

	@GetMapping(path="/count")
	public int getProductCount() {
		return productService.getTotalProductCount();
	}
}

