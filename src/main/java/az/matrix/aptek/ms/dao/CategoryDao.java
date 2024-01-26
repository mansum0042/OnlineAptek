package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.entity.Category;
import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryDao {
    Category add(Category category);

    List<Category> getAll();

    Category getById(Long id) throws NotFoundException;

    void remove(Long id);
}
