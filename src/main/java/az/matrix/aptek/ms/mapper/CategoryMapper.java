package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.CreateCategoryRequest;
import az.matrix.aptek.ms.dto.response.CategoryResponse;
import az.matrix.aptek.ms.entity.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface CategoryMapper {
    Category map(CreateCategoryRequest source);

    CategoryResponse map(Category source);

    List<CategoryResponse> map(List<Category> source);
}

