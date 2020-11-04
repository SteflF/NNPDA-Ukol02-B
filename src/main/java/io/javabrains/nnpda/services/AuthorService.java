package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findByBookId(int bookId);
}
