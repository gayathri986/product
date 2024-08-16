package com.example.product.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.product.model.Product;


@Repository
public class ProductRepository 
{
	//private final JdbcTemplate jdbcTemplate;
	
	private final NamedParameterJdbcTemplate jdbcTemplate;

//	private final ResultSetExtractor<List<Product>> resultSetExtractor = new ProductResultSetExtractor();
 
	public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//	public JdbcTemplate getJdbcTemplate()
//	{
//		return jdbcTemplate;
//	}

//	@Autowired
//	public ProductRepository(JdbcTemplate jdbcTemplate)
//	{
//		this.jdbcTemplate=jdbcTemplate;
//	}
	
	public String save(Product product)
	{
       String sql="insert into product(product_id,product_name,product_category,product_cost) values(?,?,?,?)";
       jdbcTemplate.getJdbcTemplate().update(sql,product.getProductId(),product.getProductName(),  product.getProductCategory(),product.getProductCost());
       return "useradded";
  
	}
//	public Product findById(Long id) {
//		return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?",
//	    new Object[]{id}, (r s, rowNum)-->
//		new Product (rs.getLong("productId"), rs.getString("ProductName"), rs.getDouble("ProductCost"));
//	}
//	
	public String update(Product product)
	{
		jdbcTemplate.getJdbcTemplate().update("update product set product_name=?,product_category=?,product_cost=? where product_id=?",
		product.getProductName(),product.getProductCategory(),product.getProductCost(),product.getProductId());
		return "userUpdated";
		
	}
	public String delete(Long id)
	{
		jdbcTemplate.getJdbcTemplate().update("delete from product where product_id=?",id);
		return "userdeleted";
		
		
	}
//	public List<Product> findAll()
//	{
//		return jdbcTemplate.query("select * from product",resultSetExtractor);
//	
//	}
	public List<Product> findAll() {
		String sql = "SELECT * FROM product";
		return jdbcTemplate.query(sql, (ResultSet rs) -> 
		{
			List<Product> products = new ArrayList<>();
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getLong("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setProductCategory(rs.getString("Product_category"));
				product.setProductCost(rs.getDouble("product_cost"));
				products.add(product);
			}
			return products;
		});
	}
	 @SuppressWarnings("deprecation")
	public List<Product> findProducts(int page, int size) {
	        int offset = (page - 1) * size;
	        String sql = "SELECT * FROM product LIMIT ? OFFSET ?";

	        List<Product> products = new ArrayList<>();

	        jdbcTemplate.getJdbcTemplate().query(
	            sql,
	            new Object[]{size, offset},
	            (ResultSet rs) -> {
	                while (rs.next()) {
	                    Product product = new Product();
	                    product.setProductId(rs.getLong("product_id"));
	                    product.setProductName(rs.getString("product_name"));
	                    product.setProductCategory(rs.getString("product_category"));
	                    product.setProductCost(rs.getDouble("product_cost"));
	                    products.add(product);
	                }
	            }
	        );

	        return products;
	    }
	public int countProducts() {
        String sql = "SELECT COUNT(*) FROM product";

        return jdbcTemplate.getJdbcTemplate().query(
            sql,
            (ResultSet rs) -> {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0; // Fallback in case of an empty result
            }
        );
    }
}

	
   
    

//		public Optional<Product> findById(Long id) {
//	        String sql = "SELECT * FROM product WHERE id = ?";
//	        return jdbcTemplate.query(sql, new Object[]{id}, (ResultSet rs) -> {
//	            if (rs.next()) {
//	                return Optional.of(mapRow(rs));
//	            } else {
//	                return Optional.empty();
//	            }
//	        });
//	    }
//}
//
//	public Product findById(Long id) {
//        String sql = "SELECT * FROM product WHERE id = ?";
//        return jdbcTemplate.query(sql, new Object[]{id}, (ResultSet rs) -> {
//            if (rs.next()) {
//                Product product = new Product();
//                product.setProductId(rs.getLong("product_id"));
//                product.setProductName(rs.getString("name"));
//                product.setProductCategory(rs.getDouble("price"));
//                return product;
//            }
//            return null; // or throw an exception if preferred
//        });
    


