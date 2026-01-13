package ec.edu.ups.icc.fundamentos01.categories.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

 
    boolean existsByName(String name);

    
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
}
