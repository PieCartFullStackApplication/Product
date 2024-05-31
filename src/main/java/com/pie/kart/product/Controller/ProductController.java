package com.pie.kart.product.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pie.kart.product.Model.Product;
import com.pie.kart.product.Model.ProductType;
import com.pie.kart.product.Service.GenericExceptionThrower;
import com.pie.kart.product.Service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable long id) throws GenericExceptionThrower {
        Optional<Product> product=productService.getProduct(id);
        if(product.isEmpty()){
            throw new GenericExceptionThrower("Id not found");
        }
        return productService.getProduct(id).get();

    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping
    public void removeProductById(@RequestParam long id) throws GenericExceptionThrower {
        productService.removeProduct(id);
        
    }

    @GetMapping("/type")
    public List<Product> getProductsOfGivenType(@RequestParam ProductType type) {
        return productService.getProductsOfGivenType(type);
    }

    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) throws GenericExceptionThrower {
        return productService.updateProduct(id, product);
    }
}
