package in.digitallly.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.product")
public class ProductProperties {

    /**
     * Maximum number of products returned by GET /api/products.
     * Override via env var: APP_PRODUCT_MAX_RESULTS
     */
    private int maxResults = 50;

    /**
     * When true, creating a product whose SKU already exists is rejected with 409 Conflict.
     * Override via env var: APP_PRODUCT_ENFORCE_UNIQUE_SKU
     */
    private boolean enforceUniqueSku = true;
}
