package io.javabrains.NNPDAUkol02B.repository;

import io.javabrains.NNPDAUkol02B.model.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Integer> {
    List<BookAuthor> findByAuthorId(Integer id);

    List<BookAuthor> findByBookId(Integer id);
}
