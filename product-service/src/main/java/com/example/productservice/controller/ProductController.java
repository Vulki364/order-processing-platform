package com.example.productservice.controller;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.kafka.ProductEventProducer;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductEventProducer productEventProducer;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, ProductEventProducer productEventProducer) {
        this.productService = productService;
        this.productEventProducer = productEventProducer;
    }

    @PostMapping
    public Product createProduct(@RequestBody @Valid ProductRequest request) {
        logger.info("Received request to create a new product: {}", request);
        Product savedProduct = productService.createProduct(request);
        logger.info("Product saved in DB: {} ", savedProduct);
        logger.info("Kafka event sent for product id={}", savedProduct.getId());
        return savedProduct;
    }

    @GetMapping
    public List<Product > getAllProducts() {
        List<Product> products = productService.getAllProducts();
        logger.info("Retrieved {} products", products.size());
        return products;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        logger.info("Retrieving product by id: {}", id);
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        logger.info("Retrieved product by id={}: {}", id, product);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        logger.info("Updating product id={} with data: {}", id, product);
        Product updatedProduct = productService.updateProduct(id, product);
        logger.info("Product updated id={}: {}", id, updatedProduct);
        return updatedProduct;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        logger.info("Deleting product id={}", id);
        productService.deleteProduct(id);
        logger.info("Product deleted with id={}", id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        logger.info("Fetching products by category {}", category);
        List<Product> products = productService.findByCategory(category);
        logger.info("Retrieved {} products in category={}", products.size(), category);
        return products;
    }

    @GetMapping("/price")
    public List<Product> getProductsByPriceRange(
            @RequestParam BigDecimal min, @RequestParam BigDecimal max
    ) {
        logger.info("Fetching price range from {} to {}", min, max);
        List<Product> products = productService.findByPriceRange(min, max);
        logger.info("Retrieved {} products with price from {} to {}", products.size(), min, max);
        return products;
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String text) {
        logger.info("Searching products with text {}", text);
        List<Product> products = productService.searchByText(text);
        logger.info("Retrieved {} products matching '{}'", products.size(), text);
        return products;
    }

    @GetMapping("/available")
    public List<Product> getAvailableProducts() {
        logger.info("Fetching available products");
        List<Product> products = productService.getAvailableProducts();
        logger.info("Retrieved {} available products", products.size());
        return products;
    }



}
