package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.dao.CategoryDao;
import az.matrix.aptek.ms.dto.request.CreateCategoryRequest;
import az.matrix.aptek.ms.dto.response.CategoryResponse;
import az.matrix.aptek.ms.entity.Category;
import az.matrix.aptek.ms.mapper.CategoryMapper;
import az.matrix.aptek.ms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse add(CreateCategoryRequest createCategory) {
        Category category = categoryMapper.map(createCategory);
        category = categoryDao.add(category);

        return categoryMapper.map(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryMapper.map(categoryDao.getAll());
    }

    @Override
    public void remove(Long id) {
        categoryDao.remove(id);
    }
}

