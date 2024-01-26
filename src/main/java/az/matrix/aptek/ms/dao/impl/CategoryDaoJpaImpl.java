package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.CategoryDao;
import az.matrix.aptek.ms.entity.Category;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryDaoJpaImpl implements CategoryDao {
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) throws NotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("category.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}

