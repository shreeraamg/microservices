package in.digitallly.price.service;

import in.digitallly.price.domain.dto.PriceRequest;
import in.digitallly.price.domain.dto.PriceResponse;
import in.digitallly.price.domain.persistence.Price;
import in.digitallly.price.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public List<PriceResponse> findAll() {
        return priceRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
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
        return toResponse(priceRepository.save(toEntity(request)));
    }

    public Optional<PriceResponse> update(String id, PriceRequest request) {
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

    private Price toEntity(PriceRequest request) {
        return Price.builder()
                .productId(request.getProductId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
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
