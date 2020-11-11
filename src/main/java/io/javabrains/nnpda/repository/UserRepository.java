package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    //User findByUserTokenId(Integer id);

    //User findByRecoverToken_Token(String token);
    User findByRecoverToken_Token(String recoverToken_token);
}
