package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    //User findByUserTokenId(Integer id);

    User findByUserTokenName(String token);
}
