package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.ProductManagementBackendApplication;
import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.rest.pojo.CategoryInput;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductManagementBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private CategoryRepository categoryRepository;

  private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    this.restTemplate =
            this.restTemplateBuilder.rootUri("http://localhost:" + this.port+"/product-management").build();
  }

  @Test
  void testSaveGetDeleteCategory() {

    // save category
    CategoryInput categoryInput = new CategoryInput();
    final String name = "My Category";
    categoryInput.setName(name);
    final ResponseEntity<Category> responseFromPost = restTemplate.postForEntity("/category", categoryInput, Category.class);
    assertNotNull(responseFromPost);
    assertEquals(HttpStatus.OK, responseFromPost.getStatusCode());
    assertNotNull(responseFromPost.getBody());
    assertNotNull(responseFromPost.getBody().getId());
    assertEquals(1, categoryRepository.findAll().size());
    assertEquals(name, responseFromPost.getBody().getName());

    // find by id
    ResponseEntity<Category> responseFromGet =
            restTemplate.getForEntity("/category/" + responseFromPost.getBody().getId(), Category.class);
    assertNotNull(responseFromGet);
    assertEquals(HttpStatus.OK, responseFromGet.getStatusCode());
    assertNotNull(responseFromGet.getBody());
    assertNotNull(responseFromGet.getBody().getId());
    assertEquals(responseFromPost, responseFromGet);

    // id not found
    try {
      responseFromGet = restTemplate.getForEntity("/category/asdfjakljfa" + responseFromPost.getBody().getId(), Category.class);
      fail("Exception expected");
    } catch (HttpClientErrorException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
    }

    // get all
    @SuppressWarnings("rawtypes")
    final ResponseEntity<List> responseFromGetAll =
            restTemplate.getForEntity("/category/", List.class);
    assertNotNull(responseFromGetAll);
    assertEquals(HttpStatus.OK, responseFromGetAll.getStatusCode());
    assertNotNull(responseFromGetAll.getBody());
    assertEquals(1, responseFromGetAll.getBody().size());

    // delete
    restTemplate.delete("/category/"+ responseFromPost.getBody().getId());
    assertFalse(categoryRepository.findById(responseFromPost.getBody().getId()).isPresent());


    categoryRepository.deleteAll();
  }

}
