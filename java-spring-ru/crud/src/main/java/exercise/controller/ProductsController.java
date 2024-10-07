package exercise.controller;

import java.util.List;
import java.util.Set;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotExistException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository pRepo;

    @Autowired
    private CategoryRepository cRepo;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAll() {
        return pRepo.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getCurrent(@PathVariable long id) {
        return mapper.map(pRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductCreateDTO body) {
        cRepo.findById(body.getCategoryId())
                .orElseThrow(() -> new ResourceNotExistException("Bad request"));
        return mapper.map(pRepo.save(mapper.map(body)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update (@PathVariable long id, @Valid @RequestBody ProductUpdateDTO body) {
        var product = pRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        var category = cRepo.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotExistException("Bad request"));
        category.removeProduct(product);
        mapper.update(body, product);
        category = cRepo.findById(product.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        category.addProduct(product);
        return mapper.map(pRepo.save(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable long id) {
        var task = pRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        pRepo.deleteById(task.getId());
    }
}
