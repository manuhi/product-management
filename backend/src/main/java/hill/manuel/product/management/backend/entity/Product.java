package hill.manuel.product.management.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "product")
public class Product {

  @Id
  private String id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  private Double price;

  @NotNull
  @DBRef
  private Category category;

}
