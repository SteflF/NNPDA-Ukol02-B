package io.javabrains.nnpda.repository;

import io.javabrains.nnpda.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByUserId(Integer id);
}
