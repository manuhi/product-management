package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.entity.Product;
import hill.manuel.product.management.backend.rest.pojo.ProductInput;
import hill.manuel.product.management.backend.service.ProductService;
import hill.manuel.product.management.backend.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

  private final ProductService productService;
  private final DataMapper dataMapper;

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") final String id) {
    return productService.getProductById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @PostMapping
  public ResponseEntity<?> saveProduct(@RequestBody final ProductInput productInput) {
    try {
      return ResponseEntity.ok(productService.saveOrUpdateProduct(dataMapper.mapProductFromInput(productInput), null));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@RequestBody final ProductInput productInput, @PathVariable("id") final String id) {
    try {
      return ResponseEntity.ok(productService.saveOrUpdateProduct(dataMapper.mapProductFromInput(productInput), id));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable("id") final String id) {
    productService.deleteProductById(id);
    return ResponseEntity.ok().build();
  }

}
