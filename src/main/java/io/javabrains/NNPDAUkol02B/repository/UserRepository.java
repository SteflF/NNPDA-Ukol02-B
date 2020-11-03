package io.javabrains.NNPDAUkol02B.repository;

import io.javabrains.NNPDAUkol02B.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserName(String userName);

    //User findByUserTokenId(Integer id);

    User findByUserTokenName(String token);
}
