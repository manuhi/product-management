package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.ProductManagementBackendApplication;
import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.repository.ProductRepository;
import hill.manuel.product.management.backend.rest.pojo.CategoryInput;
import hill.manuel.product.management.backend.rest.pojo.PriceInput;
import hill.manuel.product.management.backend.rest.pojo.ProductInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductManagementBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    this.restTemplate =
            this.restTemplateBuilder.rootUri("http://localhost:" + this.port+"/product-management").build();
  }

  @Test
  void testSaveUpdateGetDeleteProdcut() {

    // first create category
    final Category savedCatgory = categoryRepository.save(new Category(null, "My Category"));

    // save product
    final String productName = "My Product";
    final String prodctDescription = "My Description";
    final PriceInput priceInput = new PriceInput(22.5, "EUR");
    ProductInput productInput = new ProductInput();
    productInput.setName(productName);
    productInput.setDescription(prodctDescription);
    productInput.setPriceInput(priceInput);
    productInput.setCategoryId(savedCatgory.getId());
    final ResponseEntity<Product> responseFromPost = restTemplate.postForEntity("/product", productInput, Product.class);
    assertNotNull(responseFromPost);
    assertEquals(HttpStatus.OK, responseFromPost.getStatusCode());
    assertNotNull(responseFromPost.getBody());
    final String productId = responseFromPost.getBody().getId();
    assertNotNull(productId);
    assertTrue(productRepository.findById(productId).isPresent());
    assertEquals(productName, responseFromPost.getBody().getName());

    // update
    final String productNameUpdate = "My Product Update";
    productInput.setName(productNameUpdate);
    restTemplate.put("/product/"+productId, productInput);

    // find by id
    ResponseEntity<Product> responseFromGet =
            restTemplate.getForEntity("/product/" + productId, Product.class);
    assertNotNull(responseFromGet);
    assertEquals(HttpStatus.OK, responseFromGet.getStatusCode());
    assertNotNull(responseFromGet.getBody());
    assertNotNull(responseFromGet.getBody().getId());
    assertEquals(productNameUpdate, responseFromGet.getBody().getName());

    // id not found
    try {
      restTemplate.getForEntity("/product/asdfjakljfa", Product.class);
      fail("Exception expected");
    } catch (HttpClientErrorException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    // update not found
    try {
      restTemplate.put("/product/asdfjakljfa", productInput);
      fail("Exception expected");
    } catch (HttpClientErrorException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    // save product fail
    try {
      productInput.getPriceInput().setSymbolKey("ZZZ");
      restTemplate.postForEntity("/product", productInput, Product.class);
      fail("Exception expected");
    } catch (HttpServerErrorException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
    }

    // get all
    @SuppressWarnings("rawtypes")
    final ResponseEntity<List> responseFromGetAll =
            restTemplate.getForEntity("/product/", List.class);
    assertNotNull(responseFromGetAll);
    assertEquals(HttpStatus.OK, responseFromGetAll.getStatusCode());
    assertNotNull(responseFromGetAll.getBody());
    assertEquals(1, responseFromGetAll.getBody().size());

    // delete
    restTemplate.delete("/product/"+ productId);
    assertFalse(productRepository.findById(productId).isPresent());

    categoryRepository.deleteAll();
    productRepository.deleteAll();
  }

}
