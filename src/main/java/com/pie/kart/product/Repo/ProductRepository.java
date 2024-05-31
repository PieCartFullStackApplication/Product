package com.pie.kart.product.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pie.kart.product.Model.Product;
import com.pie.kart.product.Model.ProductType;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByProductType(ProductType type);
}
