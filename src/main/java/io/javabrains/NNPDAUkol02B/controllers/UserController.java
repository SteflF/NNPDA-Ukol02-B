package io.javabrains.NNPDAUkol02B.controllers;

import io.javabrains.NNPDAUkol02B.model.Author;
import io.javabrains.NNPDAUkol02B.model.Book;
import io.javabrains.NNPDAUkol02B.model.ChangePasswordInputModel;
import io.javabrains.NNPDAUkol02B.services.AuthorService;
import io.javabrains.NNPDAUkol02B.services.BookService;
import io.javabrains.NNPDAUkol02B.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/authors/{id}")
    public List<Author> GetAuthorsByBook(@PathVariable int id) {
        List<Author> result = authorService.findByBookId(id);

        if (result.isEmpty()) {
            return null;
        }
        else {
            return result;
        }
    }

    @PutMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/changePassword")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePasswordInputModel inputModel) {
        if (userService.changePassword(inputModel)) {
            return ResponseEntity.status(HttpStatus.OK).body("Heslo uspesne zmeneno");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Neco se nepovedlo");
    }
}
