package hill.manuel.product.management.backend.repository;


import hill.manuel.product.management.backend.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

  Optional<Category> findByName(final String name);

}
