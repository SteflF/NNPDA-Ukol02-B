package io.javabrains.NNPDAUkol02B.repository;

import io.javabrains.NNPDAUkol02B.model.UserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Integer> {
    UserToken findByName(String token);
}
