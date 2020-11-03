package io.javabrains.NNPDAUkol02B.services;

import io.javabrains.NNPDAUkol02B.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findByBookId(int bookId);
}
