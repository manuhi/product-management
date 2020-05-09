package hill.manuel.product.management.backend.service;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceTest {

  @Autowired
  private CategoryService categoryService;

  @Test
  void testSaveGetAndDeleteCategory() {

    // Insert categories

    final String categoryName1 = "1";
    final String categoryName2 = "2";
    final String categoryName3 = "3";

    final Category c1 = categoryService.saveCategory(categoryName1);
    final Category c2 = categoryService.saveCategory(categoryName2);
    final Category c3 = categoryService.saveCategory(categoryName3);

    final List<Category> allCategories = categoryService.getAllCategories();
    assertEquals(3, allCategories.size());
    assertTrue(allCategories.contains(c1));
    assertTrue(allCategories.contains(c2));
    assertTrue(allCategories.contains(c3));

    final Optional<Category> category1Optional = categoryService.getCategoryById(c1.getId());
    assertTrue(category1Optional.isPresent());
    final Category categoryFromDb = category1Optional.get();
    assertNotNull(categoryFromDb);
    assertEquals(categoryFromDb, c1);

    final String id = categoryFromDb.getId();
    categoryService.deleteCategoryById(id);

    assertFalse(categoryService.getCategoryById(id).isPresent());
  }

}
