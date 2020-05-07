package hill.manuel.product.management.backend.rest.pojo;

import lombok.Data;

@Data
public class ProductInput {

  private String id;
  private String name;
  private String description;
  private PriceInput priceInput;
  private String categoryId;

}
