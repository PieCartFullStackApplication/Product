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
    public List<Product> getProductsOfGivenType(ProductType type){
        return productRepo.findAllByProductType(type);
    }

    @Override
    public void removeProduct(long id){
        productRepo.deleteById(id);
    }

    @Override
    public Product createProduct(Product product){
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(long id, Product product) throws GenericExceptionThrower{
        if(id!=product.getId()){
             throw new GenericExceptionThrower("Product not active to be updated");
        }
        return productRepo.save(product);
    }
}
