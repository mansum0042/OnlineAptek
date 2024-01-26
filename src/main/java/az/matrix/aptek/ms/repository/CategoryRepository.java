package az.matrix.aptek.ms.repository;

import az.matrix.aptek.ms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
