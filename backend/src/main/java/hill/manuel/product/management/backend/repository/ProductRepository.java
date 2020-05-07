package hill.manuel.product.management.backend.repository;

import hill.manuel.product.management.backend.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
