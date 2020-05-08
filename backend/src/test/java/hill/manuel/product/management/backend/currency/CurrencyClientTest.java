package hill.manuel.product.management.backend.currency;

import hill.manuel.product.management.backend.rest.pojo.PriceInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CurrencyClientTest {

  @Autowired
  private CurrencyClient currencyClient;

  @Test
  void testGetAllAvailablePriceSymbols() {
    final List<String> symbols = currencyClient.getAllAvailablePriceSymbols();
    assertNotNull(symbols);
    assertTrue(symbols.contains("EUR"));
  }

  @Test
  void testGetPriceInEuroForSymbol() {
    Double price = currencyClient.getPriceInEuroForSymbol(new PriceInput(1.5, "EUR"));
    assertEquals(1.5, price);
    price = currencyClient.getPriceInEuroForSymbol(new PriceInput(1.5, "USD"));
    assertNotEquals(1.5, price);
  }

}
