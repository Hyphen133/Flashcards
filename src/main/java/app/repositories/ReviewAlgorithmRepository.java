package app.repositories;

import app.data.ReviewAlgorithm;
import org.springframework.data.repository.CrudRepository;

public interface ReviewAlgorithmRepository extends CrudRepository<ReviewAlgorithm, Integer> {
    ReviewAlgorithm findOneByName(String name);
}
