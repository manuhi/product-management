package hill.manuel.product.management.backend.util;

import hill.manuel.product.management.backend.entity.Category;
import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.currency.CurrencyClient;
import hill.manuel.product.management.backend.rest.pojo.ProductInput;
import hill.manuel.product.management.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataMapper {

  private final CurrencyClient currencyClient;
  private final CategoryService categoryService;

  public Product mapProductFromInput(final ProductInput productInput) {
    final Product product = new Product();
    product.setName(productInput.getName());
    product.setDescription(productInput.getDescription());
    product.setPrice(currencyClient.getPriceInEuroForSymbol(productInput.getPriceInput()));
    final Optional<Category> categoryOptional = categoryService.getCategoryById(productInput.getCategoryId());
    if (categoryOptional.isPresent()) {
      product.setCategory(categoryOptional.get());
    } else {
      final String errorMsg = "CategoryId not available: " + productInput.getCategoryId();
      log.error(errorMsg);
      throw new RuntimeException(errorMsg);
    }
    return product;
  }

}
