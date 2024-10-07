package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> mapper.map(product))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getCurrent(@PathVariable long id) {
        return mapper.map(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create (@RequestBody ProductCreateDTO body) {
        return mapper.map(productRepository.save(mapper.map(body)));
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable long id, @RequestBody ProductUpdateDTO body) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        mapper.update(body, product);
        return mapper.map(productRepository.save(product));
    }
}
