package in.digitallly.location.service;

import in.digitallly.location.domain.dto.LocationRequest;
import in.digitallly.location.domain.dto.LocationResponse;
import in.digitallly.location.domain.persistence.Location;
import in.digitallly.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<LocationResponse> findAll() {
        return locationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<LocationResponse> findById(String id) {
        return locationRepository.findById(id).map(this::toResponse);
    }

    public List<LocationResponse> findByCity(String city) {
        return locationRepository.findByCity(city).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<LocationResponse> findByCountry(String country) {
        return locationRepository.findByCountry(country).stream()
                .map(this::toResponse)
                .toList();
    }

    public LocationResponse create(LocationRequest request) {
        return toResponse(locationRepository.save(toEntity(request)));
    }

    public Optional<LocationResponse> update(String id, LocationRequest request) {
        return locationRepository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setAddress(request.getAddress());
            existing.setCity(request.getCity());
            existing.setCountry(request.getCountry());
            existing.setLatitude(request.getLatitude());
            existing.setLongitude(request.getLongitude());
            return toResponse(locationRepository.save(existing));
        });
    }

    public boolean delete(String id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Location toEntity(LocationRequest request) {
        return Location.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    private LocationResponse toResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .city(location.getCity())
                .country(location.getCountry())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .createdAt(location.getCreatedAt())
                .updatedAt(location.getUpdatedAt())
                .build();
    }
}
