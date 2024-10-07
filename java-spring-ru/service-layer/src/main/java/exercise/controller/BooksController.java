package exercise.controller;

import java.util.List;

import exercise.dto.*;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookDTO getCurrent(@PathVariable long id) {
        return service.getCurrent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create (@Valid @RequestBody BookCreateDTO body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO update (@Valid @RequestBody BookUpdateDTO body, @PathVariable long id) {
        return service.update(body, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
