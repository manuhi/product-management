package hill.manuel.product.management.backend.service;

import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.ProductRepository;
import hill.manuel.product.management.backend.util.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final DataMapper dataMapper;

  public Optional<Product> getProductById(final String id) {
    log.debug("Getting product for id {}", id);
    return productRepository.findById(id);
  }

  public List<Product> getAllProducts() {
    log.debug("Getting all products");
    return productRepository.findAll();
  }

  public Product saveOrUpdateProduct(@Valid final Product product, final String id) {
    if (id != null) {
      log.debug("Updating product with name {}", product.getName());
      final Optional<Product> productFromDb = getProductById(id);
      if (productFromDb.isPresent()) {
        product.setId(id);
      } else {
        final String msg = "Product with id " + id + " not found";
        log.error(msg);
        throw new RuntimeException(msg);
      }
    } else {
      log.debug("Saving product with name {}", product.getName());
    }
    return productRepository.save(product);
  }

}
