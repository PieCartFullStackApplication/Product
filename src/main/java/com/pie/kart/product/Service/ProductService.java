package com.pie.kart.product.Service;

import java.util.List;
import java.util.Optional;

import com.pie.kart.product.Model.Product;
import com.pie.kart.product.Model.ProductType;

/**
 *
 * @author yash
 */
public interface ProductService {

    public Optional<Product> getProduct(long id);

    public List<Product> getProductsOfGivenType(ProductType type);

    public List<Product> getAllProducts();

    public void removeProduct(long id);

    public Product createProduct(Product product);

    public Product updateProduct(long id, Product product) throws GenericExceptionThrower;
}
