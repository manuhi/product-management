package hill.manuel.product.management.backend.service;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  void testGetProductByIdAndGetAllProducts() {

    // Insert category and product

    final String categoryName = "1";

    final String productName1 = "Product 1";
    final String description1 = "My description 1";
    final double price1 = 1.5;

    final String productName2 = "Product 2";
    final String description2 = "My description 2";
    final double price2 = 2.75;

    final Category category = categoryRepository.save(new Category(null, categoryName));

    final Product product1 = productRepository.save(new Product(null, productName1, description1, price1, category));
    final Product product2 = productRepository.save(new Product(null, productName2, description2, price2, category));

    final List<Product> allProducts = productService.getAllProducts();
    assertEquals(2, allProducts.size());
    assertTrue(allProducts.contains(product1));
    assertTrue(allProducts.contains(product2));

    final Optional<Product> product1Optional = productService.getProductById(product1.getId());
    assertTrue(product1Optional.isPresent());
    final Product p1 = product1Optional.get();
    assertNotNull(p1);
    assertEquals(product1, p1);

    productRepository.deleteAll();
    categoryRepository.deleteAll();
  }

  @Test
  void testSaveOrUpdateAndDeleteProduct() {

    final Category category = categoryRepository.save(new Category(null, "Category 1"));

    final String productName1 = "Product 1";
    final String description1 = "My description 1";
    final double price1 = 1.5;

    // save test

    // first check if not exist
    final List<Product> allProducts = productRepository.findAll();
    assertEquals(0, allProducts.size());

    final Product product = new Product(null, productName1, description1, price1, category);
    Product savedProduct = productService.saveOrUpdateProduct(product, null);

    // find by id
    Optional<Product> productFromDb = productRepository.findById(savedProduct.getId());
    assertTrue(productFromDb.isPresent());
    assertEquals(savedProduct, productFromDb.get());

    // Update test
    savedProduct.setPrice(44.66);
    savedProduct = productService.saveOrUpdateProduct(savedProduct, savedProduct.getId());

    productFromDb = productRepository.findById(savedProduct.getId());
    assertTrue(productFromDb.isPresent());
    assertEquals(savedProduct, productFromDb.get());

    final String id = productFromDb.get().getId();
    productService.deleteProductById(id);
    assertFalse(productService.getProductById(id).isPresent());

    categoryRepository.deleteAll();
  }


}
