package com.example.product.service;

import java.util.List;

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
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

//    @Override
//    public Product getProductById(Long id) {
//        return productRepository.findById(id);
//    }

    @Override
    public void updateProduct(Long id, Product product) {
        product.setProductId(id);
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public int getTotalProductCount() {
        return productRepository.countProducts();
    }
    
    public List<Product> getProducts(int page, int size) {
        // Validate page and size to ensure they are positive
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
        return productRepository.findProducts(page, size);
    }

}
//	@Override
//	public List<Product> getProducts(int page, int size) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
//   
//    public List<Product> getProducts(int page, int size) {
//       return productRepository.findProducts(page, size);
//   }
//}
