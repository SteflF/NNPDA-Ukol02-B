package io.javabrains.NNPDAUkol02B.services;

import io.javabrains.NNPDAUkol02B.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findByUserId(int userId);
}
