package com.pie.kart.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pie.kart.product.Model.Product;
import com.pie.kart.product.Model.ProductType;
import com.pie.kart.product.Repo.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testGetProduct() throws Exception {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setProductType(ProductType.ELECTRONICS);
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setSpecification("Test Specification");
        product = productRepository.save(product);

        mockMvc.perform(get("/product/" + product.getId()))
                .andExpect(status().isOk());

        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null);
        assertEquals("Test Product", fetchedProduct.getTitle());
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setTitle("Product 1");
        product1.setProductType(ProductType.ELECTRONICS);
        product1.setPrice(100.0);
        product1.setDescription("Description 1");
        product1.setSpecification("Specification 1");

        Product product2 = new Product();
        product2.setTitle("Product 2");
        product2.setProductType(ProductType.DEFAULT);
        product2.setPrice(50.0);
        product2.setDescription("Description 2");
        product2.setSpecification("Specification 2");

        productRepository.save(product1);
        productRepository.save(product2);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk());
        Iterable<Product> allProducts = productRepository.findAll();
        assertTrue(allProducts.iterator().hasNext());
    }

    @Test
    void testRemoveProductById() throws Exception {
        Product product = new Product();
        product.setTitle("Test Product");
        product.setProductType(ProductType.ELECTRONICS);
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setSpecification("Test Specification");
        product = productRepository.save(product);

        mockMvc.perform(delete("/product/{id}", product.getId()))
        .andExpect(status().isOk());


        boolean productExists = productRepository.existsById(product.getId());
        assertTrue(!productExists);
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setTitle("New Product");
        product.setProductType(ProductType.ELECTRONICS);
        product.setPrice(150.0);
        product.setDescription("New Description");
        product.setSpecification("New Specification");

        mockMvc.perform(post("/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("New Product"));

        Product createdProduct = productRepository.findByTitle("New Product");
        assertEquals("New Product", createdProduct.getTitle());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setTitle("Original Product");
        product.setProductType(ProductType.ELECTRONICS);
        product.setPrice(200.0);
        product.setDescription("Original Description");
        product.setSpecification("Original Specification");
        product = productRepository.save(product);

        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Product");
        updatedProduct.setProductType(ProductType.ELECTRONICS);
        updatedProduct.setPrice(250.0);
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setSpecification("Updated Specification");

        mockMvc.perform(put("/product/update/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isOk());

        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null);
        assertEquals("Updated Product", fetchedProduct.getTitle());
    }
}