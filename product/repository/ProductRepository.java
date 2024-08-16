package com.example.product.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.product.model.Product;

@Repository
public class ProductRepository 
{
	private final NamedParameterJdbcTemplate jdbcTemplate;
    
	public ProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String save(Product product)
	{
		String sql="insert into product(product_id,product_name,product_category,product_cost) values(?,?,?,?)";
		jdbcTemplate.getJdbcTemplate().update(sql,product.getProductId(),product.getProductName(),  product.getProductCategory(),product.getProductCost());
		return "useradded";

	}

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

		jdbcTemplate.getJdbcTemplate().query(sql,new Object[]{size, offset},(ResultSet rs) -> {
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
					return 0; 
				}
				);
	}
}