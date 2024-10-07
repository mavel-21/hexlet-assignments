package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotExistException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper mapper;

    @Autowired
    private BookRepository bRepo;

    @Autowired
    private AuthorRepository aRepo;

    public List<BookDTO> getAll() {
        return bRepo.findAll().stream().map(mapper::map).toList();
    }

    public BookDTO getCurrent(long id) {
        return mapper.map(bRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found")));
    }

    public BookDTO create(BookCreateDTO body) {
        aRepo.findById(body.getAuthorId())
                .orElseThrow(() -> new ResourceNotExistException("Bad request"));
        return mapper.map(bRepo.save(mapper.map(body)));
    }

    public BookDTO update(BookUpdateDTO body, long id) {
        if (body.getAuthorId().isPresent())
            aRepo.findById(body.getAuthorId().get())
                    .orElseThrow(() -> new ResourceNotExistException("Bad request"));
        var book = bRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        mapper.update(body, book);
        bRepo.save(book);
        return mapper.map(book);
    }

    public void delete(Long id) {
        bRepo.deleteById(id);
    }
}
