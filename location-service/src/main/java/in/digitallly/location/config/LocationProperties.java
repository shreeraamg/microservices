package in.digitallly.location.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.location")
public class LocationProperties {

    /**
     * Maximum number of locations returned by GET /api/locations.
     * Override via env var: APP_LOCATION_MAX_RESULTS
     */
    private int maxResults = 100;

    /**
     * When true, creating a location without latitude and longitude is rejected with 400.
     * Override via env var: APP_LOCATION_COORDINATES_REQUIRED
     */
    private boolean coordinatesRequired = false;
}
