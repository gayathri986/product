package com.example.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.product.model.Product;


@Service
public class ProductService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//special character checking 
	private static boolean isStringContainsSpecialChars(String str1)
	{
		boolean status=false;
		Pattern p = Pattern.compile("[^a-z0-9 ]",Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(str1);
		boolean characterFound=m.find();
		
		if(characterFound)
		{
			System.out.println("yes,special character found ");
			status=true;
		}
		else
		{
			System.out.println("No,special character not found ");
		}
		return status;		
	}

	//Add the product details 
	public Map<String, Object> saveProduct(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result",false);
		response.put("message","Unable to connect database");

		boolean productNameSpecialCharacter=isStringContainsSpecialChars(product.getProductName());
	       
		boolean productCategorySpecialCharacter=isStringContainsSpecialChars(product.getProductCategory());
        
		
        
		if(product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim()) || (productNameSpecialCharacter)) {
			response.put("message", "Please enter valid product name");
			return response;
		}
		
		else if(product.getProductCategory()==null || "".equalsIgnoreCase(product.getProductCategory().trim()) || (productCategorySpecialCharacter))
		{
			response.put("message","please enter valid product category" );
			return response;
		}
		else if(product.getProductDescription()==null || "".equalsIgnoreCase(product.getProductDescription().trim()))
		{
			response.put("message","please enter valid product description");
			return response;
		}
		else if(product.getProductPrice()<=0) 
		{
			response.put("message","please enter valid product price");
			return response;
		}
		else if(product.getProductQuantity()<=0)
		{
			response.put("message","please enter valid  product quantity");
			return response;
		}

		try
		{
			String sql="insert into product(product_id,product_name,product_category,product_description,product_price,product_quantity) values(?,?,?,?,?,?)";
			int i=jdbcTemplate.update(sql,generateProductId(product.getProductName()),product.getProductName(),product.getProductCategory(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity());
			if(i>0)
			{
				response.put("result",true);
				response.put("message","product details added");
				return response;
			}
			else
			{
				response.put("message","product unable to add");
				return response;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			response.put("error", e.getMessage());
		}

		return response;

	}

	//update the product details
	public Map<String, Object> updateProduct(String id,Product product)
	{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "Unable to connect database!");

		boolean productNameSpecialCharacter=isStringContainsSpecialChars(product.getProductName());
       
		boolean productCategorySpecialCharacter=isStringContainsSpecialChars(product.getProductCategory());
        
		
		if (product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim()) || (productNameSpecialCharacter)) {
			response.put("message", "Please enter product name");
			return response;
		}

		else if(product.getProductCategory()==null || "".equalsIgnoreCase(product.getProductCategory().trim()) || (productCategorySpecialCharacter))
		{
			response.put("message","please enter product category" );
			return response;
		}

		else if(product.getProductDescription()==null || "".equalsIgnoreCase(product.getProductDescription().trim()))
		{
			response.put("message","please enter product description");
			return response;
		}

		else if(product.getProductPrice()<=0) 
		{
			response.put("message","please enter valid product price");
			return response;

		}

		else if(product.getProductQuantity()<=0)
		{
			response.put("message","please enter valid product quantity");
			return response;
		}
		try
		{
			String sql="update product set product_name=?,product_category=?,product_description=?,product_price=?,product_quantity=? where product_id=?";
			int i=jdbcTemplate.update(sql,product.getProductName(),product.getProductCategory(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity(),id);
			System.out.println(i);
			if(i>0)
			{
				response.put("message","product details updated");
				return response;
			}
			else
			{
				response.put("message","product details unable to update");
				return response;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	//delete the product details
	public Map<String, Object> deleteProduct(String id)
	{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");

		try
		{
			//	
			String sql="delete from product where product_id=?";
			int i=jdbcTemplate.update(sql,id);
			if(i>0)
			{
				response.put("result",true);
				response.put("message","product details deleted");
			}

			else
			{
				response.put("message","product details unable to delete");
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;

	}

	//search products by product name or product category
	public Map<String, Object> searchProducts(int page,int size,String productName,String productCategory) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");
		response.put("data", "");

		List<Map<String, Object>> result = null;
		
		List<Object> params = new ArrayList<>();

		try {

			int offset = (page - 1) * size;
			String sql = "SELECT * FROM product where 1=1";

			if (productName != null && !productName.isEmpty()) {
				sql += " AND product_name like  ?";
				params.add('%'+productName+'%');
			}
			if (productCategory != null && !productCategory.isEmpty()) {
				sql +=" AND product_category like ?";
				params.add("%"+productCategory+"%");
			}
			sql+="order by id limit ? offset ?";
			params.add(size);
			params.add(offset);
			
			result = jdbcTemplate.queryForList(sql,params.toArray());
			
			if (!result.isEmpty()) {
				response.put("result", true);
				response.put("message", "product details fetched successfully!");
				response.put("data", result);
			} 
			else {
				response.put("result", false);
				response.put("message", "unable to fetched the product details!");
			}
		} 
		catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	//get the product based on Id
	public Map<String, Object> getproductById(String productId) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> searchResults = null;
		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		if (productId == null ||"".equalsIgnoreCase(productId.trim())) {
			response.put("message", "Please provide product Id");
			return response;
		}
		try {
			String sql = "select product_name,product_category,product_description,product_price,product_quantity from product where product_id=?";
			searchResults = jdbcTemplate.queryForList(sql, productId);
			if (!searchResults.isEmpty()) {
				response.put("success", true);
				response.put("message", "Data fetched successfully!");
				response.put("data", searchResults);
			} 
			else {
				response.put("message", "Unable to fetched details!");
			}
		} 
		catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}


	//Count the number of products
	public int getTotalProductCount() {
		String sql = "SELECT COUNT(*) FROM product";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	
	//generate random number for productId
	private String generateProductId(String productName) {
		String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();
		Random random = new Random();
		int randomNumber = 1000 + random.nextInt(9000);
		return productIdPrefix + randomNumber;
	}
}
