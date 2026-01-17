package com.example.productservice.repository;

import com.example.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description
    );

    List<Product> findByAvailableTrue();

}
