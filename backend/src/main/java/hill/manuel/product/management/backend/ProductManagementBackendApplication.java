package hill.manuel.product.management.backend;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@SpringBootApplication
public class ProductManagementBackendApplication {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  public static void main(String[] args) {
    SpringApplication.run(ProductManagementBackendApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onStartup() {

    productRepository.deleteAll();
    categoryRepository.deleteAll();

    categoryRepository.saveAll(Arrays.asList(new Category(null, "1")
            , new Category(null, "2"), new Category(null, "3"), new Category(null, "4")));

    final List<Category> all = categoryRepository.findAll();
    System.out.println(all);

    final Optional<Category> cat = categoryRepository.findByName("1");

    productRepository.save(new Product(null, "Product 1", "My description", "my price", cat.get()));

    System.out.println(productRepository.findAll());

  }
}
