package hill.manuel.product.management.backend.fixer;

import hill.manuel.product.management.backend.rest.pojo.PriceInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyClient {

  public static final String BASE_CURRENCY = "EUR";

  private RestTemplate restTemplate;

  @Value("${product-management.currency.base-url}")
  private String baseUrl;

  @PostConstruct
  public void init() {
    restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
  }

  @Cacheable(value = "priceSymbols")
  public List<String> getAllAvailablePriceSymbols() {
    List<String> priceSymbols = new ArrayList<>();
    final ResponseEntity<SymbolResponse> response = restTemplate.getForEntity("/latest", SymbolResponse.class);
    if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
      if (response.getBody() != null) {
        priceSymbols.add(response.getBody().getBase());
        final Map<String, Double> rates = response.getBody().getRates();
        priceSymbols.addAll(rates.keySet());
        return priceSymbols;
      }
    }

    final String errorMsg = "Failed to read symbols from exchangeratesapi";
    log.error(errorMsg);
    throw new RuntimeException(errorMsg);
  }

  public Double getPriceInEuroForSymbol(final PriceInput priceInput) {
    final String symbol = priceInput.getSymbolKey();
    if (!getAllAvailablePriceSymbols().contains(symbol)) {
      final String errorMsg = "Symbol " + symbol + " is not avabilable";
      log.error(errorMsg);
      throw new RuntimeException(errorMsg);
    }

    if (symbol.equals(BASE_CURRENCY)) {
      log.info("Currency is already " + BASE_CURRENCY);
      return 1.0;
    }

    Map<String, String> uriVariables = new LinkedHashMap<>();
    uriVariables.put("base", symbol);
    final ResponseEntity<SymbolResponse> response = restTemplate.getForEntity("/latest", SymbolResponse.class, uriVariables);
    if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
      if (response.getBody() != null) {
        final Map<String, Double> rates = response.getBody().getRates();
        for (String s : rates.keySet()) {
          if (s.equals(BASE_CURRENCY)) {
            return priceInput.getValue() * rates.get(s);
          }
        }
      }
    }

    final String errorMsg = "Failed to read rates from exchangeratesapi";
    log.error(errorMsg);
    throw new RuntimeException(errorMsg);
  }

}
