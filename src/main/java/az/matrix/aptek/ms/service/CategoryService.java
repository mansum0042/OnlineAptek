package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.CreateCategoryRequest;
import az.matrix.aptek.ms.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponse add(CreateCategoryRequest createCategory);

    List<CategoryResponse> getAll();

    void remove(Long id);
}
