package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.Author;
import io.javabrains.nnpda.model.BookAuthor;
import io.javabrains.nnpda.repository.BookAuthorRepository;
import io.javabrains.nnpda.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Override
    public List<Author> findByBookId(int bookId) {
        List<BookAuthor> bookAuthors = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        bookAuthorRepository.findByBookId(bookId).iterator().forEachRemaining(bookAuthors::add);

        for (var bookAuthor:bookAuthors
        ) {
            authors.add(bookAuthor.getAuthor());
        }

        return authors;
    }
}
