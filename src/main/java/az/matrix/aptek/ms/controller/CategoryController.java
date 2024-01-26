package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.CreateCategoryRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.CategoryResponse;
import az.matrix.aptek.ms.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponse>> add(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.add(request);

        BaseResponse<CategoryResponse> baseResponse = BaseResponse.<CategoryResponse>builder().data(categoryResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getAll() {
        List<CategoryResponse> categoryResponses = categoryService.getAll();

        BaseResponse<List<CategoryResponse>> baseResponse = BaseResponse.<List<CategoryResponse>>builder()
                .data(categoryResponses).build();
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        categoryService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(messageSource
                .getMessage("category.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }
}
