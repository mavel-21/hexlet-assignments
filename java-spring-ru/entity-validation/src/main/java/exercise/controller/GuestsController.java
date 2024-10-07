package exercise.controller;

import exercise.mapper.GuestMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.GuestRepository;
import exercise.dto.GuestDTO;
import exercise.dto.GuestCreateDTO;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/guests")
public class GuestsController {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper mapper;

    @GetMapping(path = "")
    public List<GuestDTO> index() {
        var products = guestRepository.findAll();
        return products.stream()
                .map(p -> mapper.map(p))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public GuestDTO show(@PathVariable long id) {

        var guest =  guestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Guest with id " + id + " not found"));
        var guestDto = mapper.map(guest);
        return guestDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDTO create (@Valid @RequestBody GuestCreateDTO body) {
        return mapper.map(guestRepository.save(mapper.map(body)));
    }
}
