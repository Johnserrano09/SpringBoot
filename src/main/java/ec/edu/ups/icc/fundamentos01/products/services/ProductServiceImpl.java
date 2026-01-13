package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return repo.findAll()
            .stream()
            .map(Product::fromEntity)
            .map(ProductMapper::toResponse)
            .toList();
    }

    @Override
    public ProductResponseDto findOne(int id) {
        return repo.findById((long) id)
            .map(Product::fromEntity)
            .map(ProductMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        // Regla de negocio: nombre único
        if (repo.existsByName(dto.name)) {
            throw new ConflictException("El nombre ya está registrado");
        }

        var saved = repo.save(Product.fromCreateDto(dto).toEntity());
        return ProductMapper.toResponse(Product.fromEntity(saved));
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        return repo.findById((long) id)
            .map(Product::fromEntity)
            .map(p -> p.update(dto))
            .map(Product::toEntity)
            .map(repo::save)
            .map(Product::fromEntity)
            .map(ProductMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        return repo.findById((long) id)
            .map(Product::fromEntity)
            .map(p -> p.partialUpdate(dto))
            .map(Product::toEntity)
            .map(repo::save)
            .map(Product::fromEntity)
            .map(ProductMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Override
    public void delete(int id) {
        repo.findById((long) id)
            .ifPresentOrElse(repo::delete,
                () -> { throw new NotFoundException("Producto no encontrado"); });
    }

    @Override
    public boolean validateName(Integer id, String name) {
        repo.findByName(name)
            .ifPresent(existing -> {
                if (id == null || existing.getId().longValue() != id.longValue()) {
                    throw new ConflictException(
                        "Ya existe un producto con el nombre: " + name);
                }
            });
        return true;
    }

    public ProductResponseDto secureUpdate(int id, SecureUpdate dto){

        productEntity = repo.findById((long) id)
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
            if(dto.price != null && dto.price > 100){
                throw new Buni
            }
    }
}
