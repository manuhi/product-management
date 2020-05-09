package hill.manuel.product.management.backend.service;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Optional<Category> getCategoryById(final String id) {
    return categoryRepository.findById(id);
  }

  public Category saveCategory(final String name) {
    log.debug("Saving category with name {}", name);
    return categoryRepository.save(new Category(null, name));
  }

  public void deleteCategoryById(final String id) {
    log.debug("Deleting category with id {}", id);
    categoryRepository.deleteById(id);
  }

}
