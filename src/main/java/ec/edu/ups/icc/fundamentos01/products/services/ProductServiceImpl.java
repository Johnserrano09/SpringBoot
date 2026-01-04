package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
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
            .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        // Save entity and convert to response DTO
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
            .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
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
            .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
    }

    @Override
    public void delete(int id) {
        repo.findById((long) id)
            .ifPresentOrElse(repo::delete,
                () -> { throw new IllegalStateException("Producto no encontrado"); });
    }
}
