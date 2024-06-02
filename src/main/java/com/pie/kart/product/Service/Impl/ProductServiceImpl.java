package com.pie.kart.product.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pie.kart.product.Model.Product;
import com.pie.kart.product.Model.ProductType;
import com.pie.kart.product.Repo.ProductRepository;
import com.pie.kart.product.Service.GenericExceptionThrower;
import com.pie.kart.product.Service.ProductService;

/**
 *
 * @author yash
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public Optional<Product> getProduct(long id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsOfGivenType(ProductType type) {
        return productRepo.findAllByProductType(type);
    }

    @Override
    public void removeProduct(long id) {
        productRepo.deleteById(id);
    }


    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    

    @Override
    public Product updateProduct(long id, Product product) throws GenericExceptionThrower {
        Optional<Product> existingProductOptional = productRepo.findById(id);
        if (existingProductOptional.isEmpty()) {
            throw new GenericExceptionThrower("Product not found");
        }
        Product existingProduct = existingProductOptional.get();
        if (existingProduct.getId() != id) {
            throw new GenericExceptionThrower("Product ID mismatch");
        }
        existingProduct.setTitle(product.getTitle());
        existingProduct.setProductType(product.getProductType());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setSpecification(product.getSpecification());
        return productRepo.save(existingProduct);
    }
}
