package com.store.store.product;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product with id " + productId + " was not found"));
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public void delete(Long productId) {
        boolean exist = productRepository.existsById(productId);
        if(exist)
            productRepository.deleteById(productId);
        else
            throw new EntityNotFoundException("Product with id " + productId + " was not found");
    }

    public void update(Long productId, Product product) {
        boolean exist = productRepository.existsById(productId);
        if(exist) {
            product.setId(productId);
            productRepository.save(product);
        }else
            throw new EntityNotFoundException("Product with id " + productId + " was not found");
    }

}
