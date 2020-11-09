package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.Author;
import io.javabrains.nnpda.services.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/bookAuthors")
    public List<Author> GetBookAuthors(@PathVariable int id) {
        List<Author> result = authorService.findByBookId(id);

        if (result.isEmpty()) {
            return null;
        }
        else {
            return result;
        }
    }
}
