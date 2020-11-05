package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.Book;
import io.javabrains.nnpda.repository.BookRepository;
import io.javabrains.nnpda.services.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByUserId(int userId) {
        List<Book> books = new ArrayList<>();
        List<Book> result = new ArrayList<>();

        bookRepository.findByUserId(userId).iterator().forEachRemaining(books::add);

        for (var book:books
        ) {
            Book temp = new Book();
            temp.setId(book.getId());
            temp.setName(book.getName());

            result.add(temp);
        }

        return result;
    }
}
