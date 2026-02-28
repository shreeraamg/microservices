package in.digitallly.price.controller;

import in.digitallly.price.domain.dto.PriceRequest;
import in.digitallly.price.domain.dto.PriceResponse;
import in.digitallly.price.service.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public List<PriceResponse> getAll() {
        return priceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceResponse> getById(@PathVariable String id) {
        return priceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public List<PriceResponse> getByProductId(@PathVariable String productId) {
        return priceService.findByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<PriceResponse> create(@Valid @RequestBody PriceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceResponse> update(@PathVariable String id, @Valid @RequestBody PriceRequest request) {
        return priceService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return priceService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
