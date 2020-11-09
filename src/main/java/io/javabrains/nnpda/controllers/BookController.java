package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.Book;
import io.javabrains.nnpda.services.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/userBooks")
    @ResponseBody
    public List<Book> GetUserBooks(@PathVariable int id) {
        List<Book> result = bookService.findByUserId(id);

        if (result.isEmpty()) {
            return null;
        }
        else {
            return result;
        }
    }
}
