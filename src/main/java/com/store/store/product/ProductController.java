package com.store.store.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_OWNER')")
    @GetMapping(path = "{productId}")
    public Product getProduct(@PathVariable("productId") Long productId){
        return productService.getProduct(productId);
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_OWNER')")
    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping
    public void add (@RequestBody Product product){
        productService.add(product);
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @DeleteMapping(path = "{productId}")
    public void delete (@PathVariable("productId") Long productId){
        productService.delete(productId);
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PutMapping(path = "{productId}")
    public void update(@PathVariable("productId") Long productId, @RequestBody Product product){
        productService.update(productId,product);
    }
}
