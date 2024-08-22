package com.example.product.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.product.model.Product;

@Repository
public class ProductRepository  {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//Add the product details 
	public Map save(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result",false);
		response.put("message","Unable to connect database");

//		String name = product.getProductName();
//		String productId = generateProductId(name);
//		String category = product.getProductCategory();
//		String description=product.getProductDescription();
//		double price = product.getProductPrice();
//		int quantity = product.getProductQuantity();
//		
		
		
		if (product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim())) {
			response.put("message", "Please enter product name");
			return response;
		}
		else if(product.getProductCategory()==null || "".equalsIgnoreCase(product.getProductCategory().trim()))
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
			response.put("message","please enter valid  product quantity");
			return response;
		}

		try
		{
			String sql="insert into product(product_id,product_name,product_category,product_description,product_price,product_quantity) values(?,?,?,?,?,?)";
		//	int i=jdbcTemplate.getJdbcTemplate().update(sql,generateProductId(product.getProductName()),product.getProductName(),product.getProductCategory(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity());
		     int i=jdbcTemplate.getJdbcTemplate().update(sql,generateProductId(product.getProductName()),product.getProductName(),product.getProductCategory(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity());
//			int i = jdbcTemplate.getJdbcTemplate().update(sql,productId,name,category,description,price,quantity);
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
			e.printStackTrace();
		}

		return response;

	}

	//update the product details
	public Map update(Product product)
	{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "Unable to connect database!");


		if (product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim())) {
			response.put("message", "Please enter product name");
			return response;
		}

		else if(product.getProductCategory()==null || "".equalsIgnoreCase(product.getProductCategory().trim()))
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
			int i=jdbcTemplate.getJdbcTemplate().update(sql,product.getProductName(),product.getProductCategory(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity(),product.getProductId());
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
			e.printStackTrace();
		}
		return response;
	}

	//delete the product details
	public Map delete(String id)
	{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");

		try
		{
			//	
			String sql="delete from product where product_id=?";
			int i=jdbcTemplate.getJdbcTemplate().update(sql,id);
			if(i>0)
			{
				response.put("result",true);
				response.put("message","product details deleted");
			}

			else
			{
				response.put("message","please enter valid productId");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;

	}
   
	

	public List<Map<String, Object>> allproducts() {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String sql = " select * from product";
		result =jdbcTemplate.getJdbcTemplate().queryForList(sql);
		return result;

	}
	public Map getbyId(String productId)  
	{
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> searchResults = null;
		List<Map<String, Object>> mainList = new ArrayList();
		//response.put("result", false);
		response.put("message", "Unable to connect database!");

		
	
		try {
			String sql = "select product_name,product_category,product_description,product_price,product_quantity from product where product_id=?";
			searchResults = jdbcTemplate.getJdbcTemplate().queryForList(sql, productId);
			for (Map<String, Object> result : searchResults) {
				Map<String,Object> eventMap = new HashMap<>();
				eventMap.put("productname",result.get("product_name"));
				eventMap.put("productcategory",result.get("product_category"));
				eventMap.put("productdescription",result.get("product_description"));
				eventMap.put("productprice",result.get("product_price"));
				eventMap.put("productquantity",result.get("product_quantity"));
				mainList.add(eventMap);
				System.out.println(mainList);
				response.put("success", true);
				response.put("message", "Data fetched successfully!");
				response.put("data", mainList);
				return response;
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
		

//	public List<Product> findAll() {
//		String sql = "SELECT * FROM product";
//		return jdbcTemplate.query(sql, (ResultSet rs) 
//		{
//			List<Product> products = new ArrayList<>();
//			while (rs.next()) {
//				Product product = new Product();
//				product.setProductId(rs.getString("product_id"));
//				product.setProductName(rs.getString("product_name"));
//				product.setProductCategory(rs.getString("Product_category"));
//				product.setProductDescription(rs.getString("product_description"));
//				product.setProductPrice(rs.getDouble("product_price"));
//				product.setProductQuantity(rs.getInt("product_quantity"));
//				products.add(product);
//			}
//			return products;
//		});
//	}

//	@SuppressWarnings("deprecation")
//	public List<Product> findProducts(int page, int size) {
//		int offset = (page - 1) * size;
//		String sql = "SELECT * FROM product LIMIT ? OFFSET ?";
//
//		List<Product> products = new ArrayList<>();
//
//		jdbcTemplate.getJdbcTemplate().query(sql,new Object[]{size, offset},(ResultSet rs) -> {
//			while (rs.next()) {
//				Product product = new Product();
//				product.setProductId(rs.getString("product_id"));
//				product.setProductName(rs.getString("product_name"));
//				product.setProductCategory(rs.getString("product_category"));
//				product.setProductPrice(rs.getDouble("product_price"));
//				products.add(product);
//			}
//		}
//				);
//
//		return products;
//	}

	public int countProducts() 
	{
		String sql = "SELECT COUNT(*) FROM product";
        return jdbcTemplate.getJdbcTemplate().query(sql,(ResultSet rs) -> 
		{
			if (rs.next()) 
			{
				return rs.getInt(1);
			}
			 return 0; 
		}
				);
	}

private String generateProductId(String productName) {

	String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();
	Random random = new Random();
	int randomNumber = 1000 + random.nextInt(9000);
	return productIdPrefix + randomNumber;
}

}
