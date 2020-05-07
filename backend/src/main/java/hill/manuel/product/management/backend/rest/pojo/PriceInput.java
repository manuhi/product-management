package hill.manuel.product.management.backend.rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceInput {

  private Double value;
  private String symbolKey;

}
