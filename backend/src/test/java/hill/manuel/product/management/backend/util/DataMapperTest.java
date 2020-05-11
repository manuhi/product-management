package hill.manuel.product.management.backend.util;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.repository.CategoryRepository;
import hill.manuel.product.management.backend.rest.pojo.PriceInput;
import hill.manuel.product.management.backend.rest.pojo.ProductInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataMapperTest {

  @Autowired
  private DataMapper dataMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testMapProductFromInput() {

    // create category
    final Category category = categoryRepository.save(new Category(null, "My Category"));

    final String name = "My name";
    final String description = "My description";
    final PriceInput priceInput = new PriceInput(11.5, "EUR");

    ProductInput productInput = new ProductInput();
    productInput.setName(name);
    productInput.setDescription(description);
    productInput.setPriceInput(priceInput);
    productInput.setCategoryId(category.getId());
    final Product mappedProduct = dataMapper.mapProductFromInput(productInput);
    assertNotNull(mappedProduct);
    assertEquals(name, mappedProduct.getName());
    assertEquals(description, mappedProduct.getDescription());
    assertEquals(priceInput.getValue(), mappedProduct.getPrice());
    assertEquals(category, mappedProduct.getCategory());

    categoryRepository.deleteAll();

    // category not found
    try {
      dataMapper.mapProductFromInput(productInput);
      fail("Exception expected");
    } catch (RuntimeException e) {
      assertNotNull(e);
    }

  }

}
