package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ===== Getters y Setters =====
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    // ================= FACTORY METHODS =================

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
            entity.getId().intValue(),
            entity.getName(),
            entity.getPrice(),
            entity.getStock()
        );
    }

    public static Product fromCreateDto(CreateProductDto dto) {
        return new Product(0, dto.name, dto.price, dto.stock);
    }

    // ================= CONVERSIONS =================

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

    // ================= BUSINESS LOGIC =================

    public Product update(UpdateProductDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
        return this;
    }

    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null) this.name = dto.name;
        if (dto.price != null) this.price = dto.price;
        if (dto.stock != null) this.stock = dto.stock;
        return this;
    }
}
