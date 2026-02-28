package in.digitallly.location.repository;

import in.digitallly.location.domain.persistence.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

    List<Location> findByCity(String city);

    List<Location> findByCountry(String country);
}
