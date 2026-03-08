package in.digitallly.price.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.price")
public class PriceProperties {

    /**
     * Accepted ISO 4217 currency codes. Prices with any other currency are rejected with 400.
     * Override via env var: APP_PRICE_SUPPORTED_CURRENCIES (comma-separated)
     */
    private List<String> supportedCurrencies = List.of("USD", "EUR", "GBP", "INR");

    /**
     * Maximum number of prices returned by GET /api/prices.
     * Override via env var: APP_PRICE_MAX_RESULTS
     */
    private int maxResults = 200;
}
