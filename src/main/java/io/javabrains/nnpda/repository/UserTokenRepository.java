package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.UserToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Integer> {
    UserToken findByName(String token);
}