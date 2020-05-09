package hill.manuel.product.management.backend.rest;

import hill.manuel.product.management.backend.currency.CurrencyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@CrossOrigin
public class CurrencyController {

  private final CurrencyClient currencyClient;

  @GetMapping
  public ResponseEntity<List<String>> getAllAvailablePriceSymbols() {
    return ResponseEntity.ok(currencyClient.getAllAvailablePriceSymbols());
  }

}
