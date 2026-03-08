package in.digitallly.product.service;

import in.digitallly.product.config.ProductProperties;
import in.digitallly.product.domain.dto.ProductRequest;
import in.digitallly.product.domain.dto.ProductResponse;
import in.digitallly.product.domain.persistence.Product;
import in.digitallly.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductProperties properties;

    public List<ProductResponse> findAll() {
        return productRepository.findAll(PageRequest.of(0, properties.getMaxResults()))
                .map(this::toResponse)
                .getContent();
    }

    public Optional<ProductResponse> findById(String id) {
        return productRepository.findById(id).map(this::toResponse);
    }

    public Optional<ProductResponse> findBySku(String sku) {
        return productRepository.findBySku(sku).map(this::toResponse);
    }

    public List<ProductResponse> findByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse create(ProductRequest request) {
        if (properties.isEnforceUniqueSku()
                && productRepository.findBySku(request.getSku()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A product with SKU '" + request.getSku() + "' already exists");
        }
        return toResponse(productRepository.save(toEntity(request)));
    }

    public Optional<ProductResponse> update(String id, ProductRequest request) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setSku(request.getSku());
            existing.setCategory(request.getCategory());
            return toResponse(productRepository.save(existing));
        });
    }

    public boolean delete(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .sku(request.getSku())
                .category(request.getCategory())
                .build();
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
