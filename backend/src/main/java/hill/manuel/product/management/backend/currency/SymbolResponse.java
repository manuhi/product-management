package hill.manuel.product.management.backend.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolResponse {
    private Map<String, Double> rates;
    private String base;
    private String date;
}
