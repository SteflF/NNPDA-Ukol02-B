package io.javabrains.NNPDAUkol02B.repository;

import io.javabrains.NNPDAUkol02B.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
