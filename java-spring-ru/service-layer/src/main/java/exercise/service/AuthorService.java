package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorMapper mapper;

    @Autowired
    private AuthorRepository repo;

    public List<AuthorDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public AuthorDTO getCurrent(Long id) {
        return mapper.map(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found")));
    }

    public AuthorDTO create(AuthorCreateDTO body) {
        return mapper.map(repo.save(mapper.map(body)));
    }

    public AuthorDTO update(AuthorUpdateDTO body, Long id) {
        var author = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        mapper.update(body, author);
        repo.save(author);
        return mapper.map(author);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
