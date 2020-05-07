package hill.manuel.product.management.backend.service;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Optional<Category> getCategoryById(final String id) {
    return categoryRepository.findById(id);
  }

}
