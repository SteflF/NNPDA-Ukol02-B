package io.javabrains.NNPDAUkol02B.services.impl;

import io.javabrains.NNPDAUkol02B.model.Author;
import io.javabrains.NNPDAUkol02B.model.BookAuthor;
import io.javabrains.NNPDAUkol02B.repository.BookAuthorRepository;
import io.javabrains.NNPDAUkol02B.services.AuthorService;
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
