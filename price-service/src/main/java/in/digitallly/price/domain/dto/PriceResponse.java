package in.digitallly.price.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {

    private String id;

    private String productId;

    private Double amount;

    private String currency;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
