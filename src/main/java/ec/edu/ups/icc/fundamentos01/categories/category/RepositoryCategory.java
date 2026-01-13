package ec.edu.ups.icc.fundamentos01.categories.category;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCategory extends JpaRepository<CategoryEntity, Long> {

boolean existsByName(String name);
    
Optional<CategoryEntity> findByName(String name);
}
