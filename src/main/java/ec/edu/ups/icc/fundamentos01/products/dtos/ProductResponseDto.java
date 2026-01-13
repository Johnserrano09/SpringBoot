package ec.edu.ups.icc.fundamentos01.products.dtos;

import ec.edu.ups.icc.fundamentos01.categories.dto.CategoriaResponseDto.CategorySummaryDto;

public class ProductResponseDto {
    public int id;
    public String name;
    public double price;
    public int stock;

    //aparesca sus categorias y su dueño
    public UserSummaryDto userId;



    public CategorySummaryDto category;

    public static class UserSummaryDto {
        public int id;
        public String name;
        public String email;
    }

    
}
