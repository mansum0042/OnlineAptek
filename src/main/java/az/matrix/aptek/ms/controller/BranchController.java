package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.CreateBranchRequest;
import az.matrix.aptek.ms.dto.request.UpdateBranchRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.BranchResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<PageResponse<BranchResponse>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<BranchResponse> branches = branchService.getAll(page, limit);

        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BranchResponse>> getById(
            @PathVariable Long id) throws NotFoundException {
        BranchResponse branchResponse = branchService.getById(id);

        BaseResponse<BranchResponse> baseResponse = BaseResponse.<BranchResponse>builder().data(branchResponse).build();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<BranchResponse>> add(@RequestBody @Valid CreateBranchRequest request)
            throws AlreadyExistException, NotFoundException {
        BranchResponse branch = branchService.add(request);

        BaseResponse<BranchResponse> baseResponse = BaseResponse.<BranchResponse>builder().data(branch).build();
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping
    public ResponseEntity<BaseResponse<BranchResponse>> update(@RequestBody UpdateBranchRequest request)
            throws AlreadyExistException, NotFoundException {
        BranchResponse branch = branchService.update(request);

        BaseResponse<BranchResponse> baseResponse = BaseResponse.<BranchResponse>builder().data(branch).build();
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> delete() throws NotFoundException {
        branchService.remove();

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder()
                .successMsg(messageSource.getMessage("branch.deleted", null,
                        LocaleContextHolder.getLocale())).build();
        return ResponseEntity.ok(baseResponse);
    }
}
