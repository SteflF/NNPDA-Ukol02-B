package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findByUserId(int userId);
}
