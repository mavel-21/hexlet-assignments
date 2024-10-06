package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO post(@RequestBody ContactCreateDTO con) {
        var post = contactRepository.save(toEntity(con));
        return toDto(post);
    }

    private Contact toEntity (ContactCreateDTO con) {
        var entity = new Contact();
        entity.setFirstName(con.getFirstName());
        entity.setLastName(con.getLastName());
        entity.setPhone(con.getPhone());
        return entity;
    }

    private ContactDTO toDto (Contact con) {
        var dto = new ContactDTO();
        dto.setId(con.getId());
        dto.setFirstName(con.getFirstName());
        dto.setLastName(con.getLastName());
        dto.setPhone(con.getPhone());
        dto.setUpdatedAt(con.getUpdatedAt());
        dto.setCreatedAt(con.getCreatedAt());
        return dto;
    }
}
