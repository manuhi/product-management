package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.rest.pojo.CategoryInput;
import hill.manuel.product.management.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable("id") final String id) {
    return categoryService.getCategoryById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @PostMapping
  public ResponseEntity<Category> saveCategory(@RequestBody final CategoryInput categoryInput) {
    return ResponseEntity.ok(categoryService.saveCategory(categoryInput.getName()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable("id") final String id) {
    categoryService.deleteCategoryById(id);
    return ResponseEntity.ok().build();
  }

}
