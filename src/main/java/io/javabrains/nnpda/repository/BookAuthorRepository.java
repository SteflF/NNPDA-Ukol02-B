package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Integer> {
    List<BookAuthor> findByAuthorId(Integer id);

    List<BookAuthor> findByBookId(Integer id);
}
