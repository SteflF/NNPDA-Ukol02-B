package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.db.RecoverToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoverTokenRepository extends CrudRepository<RecoverToken, Integer> {
    RecoverToken findByToken(String token);
}
