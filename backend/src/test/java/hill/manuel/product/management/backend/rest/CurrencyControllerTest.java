package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.ProductManagementBackendApplication;
import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.repository.ProductRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductManagementBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    this.restTemplate =
            this.restTemplateBuilder.rootUri("http://localhost:" + this.port+"/product-management").build();
  }

  @Test
  void testGetAllAvailablePriceSymbols() {
    @SuppressWarnings("rawtypes")
    final ResponseEntity<List> response = restTemplate.getForEntity("/currency", List.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().size() > 0);
    assertTrue(response.getBody().contains("EUR"));
  }

}
