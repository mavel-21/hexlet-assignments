package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AuthorDTO getCurrent(@PathVariable long id) {
        return service.getCurrent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create (@Valid @RequestBody AuthorCreateDTO body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO update (@Valid @RequestBody AuthorUpdateDTO body, @PathVariable long id) {
        return service.update(body, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
