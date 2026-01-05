package ec.edu.ups.icc.fundamentos01.products.controllers;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductsController {


    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }


    // GET ALL
    @GetMapping
    public java.util.List<ProductResponseDto> findAll() {
        return service.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public ProductResponseDto findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    // POST
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody CreateProductDto dto) {
        return service.create(dto);
    }

    // PUT
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable int id, @Valid @RequestBody UpdateProductDto dto) {
        return service.update(id, dto);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ProductResponseDto partialUpdate(@PathVariable int id, @Valid @RequestBody PartialUpdateProductDto dto) {
        return service.partialUpdate(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable int id) {
        service.delete(id);
        return Map.of("message", "Deleted successfully");
    }

    // Exception handler for controlled errors
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(IllegalStateException ex) {
        return Map.of("error", ex.getMessage());
    }

    // Validation exception (DTO validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> e.getDefaultMessage())
            .collect(Collectors.toList());
        return Map.of("errors", errors);
    }

    // Business validation errors (domain model)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

}
            