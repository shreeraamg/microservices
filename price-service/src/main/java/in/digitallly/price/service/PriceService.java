package in.digitallly.price.service;

import in.digitallly.price.config.PriceProperties;
import in.digitallly.price.domain.dto.PriceRequest;
import in.digitallly.price.domain.dto.PriceResponse;
import in.digitallly.price.domain.persistence.Price;
import in.digitallly.price.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceProperties properties;

    public List<PriceResponse> findAll() {
        return priceRepository.findAll(PageRequest.of(0, properties.getMaxResults()))
                .map(this::toResponse)
                .getContent();
    }

    public Optional<PriceResponse> findById(String id) {
        return priceRepository.findById(id).map(this::toResponse);
    }

    public List<PriceResponse> findByProductId(String productId) {
        return priceRepository.findByProductId(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PriceResponse create(PriceRequest request) {
        validateCurrency(request.getCurrency());
        return toResponse(priceRepository.save(toEntity(request)));
    }

    public Optional<PriceResponse> update(String id, PriceRequest request) {
        validateCurrency(request.getCurrency());
        return priceRepository.findById(id).map(existing -> {
            existing.setProductId(request.getProductId());
            existing.setAmount(request.getAmount());
            existing.setCurrency(request.getCurrency());
            return toResponse(priceRepository.save(existing));
        });
    }

    public boolean delete(String id) {
        if (priceRepository.existsById(id)) {
            priceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateCurrency(String currency) {
        if (!properties.getSupportedCurrencies().contains(currency.toUpperCase())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Currency '" + currency + "' is not supported. Accepted: "
                            + properties.getSupportedCurrencies());
        }
    }

    private Price toEntity(PriceRequest request) {
        return Price.builder()
                .productId(request.getProductId())
                .amount(request.getAmount())
                .currency(request.getCurrency().toUpperCase())
                .build();
    }

    private PriceResponse toResponse(Price price) {
        return PriceResponse.builder()
                .id(price.getId())
                .productId(price.getProductId())
                .amount(price.getAmount())
                .currency(price.getCurrency())
                .createdAt(price.getCreatedAt())
                .updatedAt(price.getUpdatedAt())
                .build();
    }
}
