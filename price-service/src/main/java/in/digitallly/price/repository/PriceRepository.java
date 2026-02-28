package in.digitallly.price.repository;

import in.digitallly.price.domain.persistence.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends MongoRepository<Price, String> {

    List<Price> findByProductId(String productId);
}
