package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.rest.pojo.ProductInput;
import hill.manuel.product.management.backend.service.ProductService;
import hill.manuel.product.management.backend.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;
  private final DataMapper dataMapper;

  @GetMapping("/{id}")
  public Optional<Product> getProductById(@PathVariable("id") final String id) {
    return productService.getProductById(id);
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public Product saveProduct(@RequestBody final ProductInput productInput) {
    return productService.saveOrUpdateProduct(dataMapper.mapProductFromInput(productInput), null);
  }

  @PutMapping("/{id}")
  public Product updateProduct(@RequestBody final ProductInput productInput, @PathVariable("id") final String id) {
    return productService.saveOrUpdateProduct(dataMapper.mapProductFromInput(productInput), id);
  }

}
