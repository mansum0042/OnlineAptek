package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.UpdateBasketRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.BasketResponse;
import az.matrix.aptek.ms.dto.response.FullBasketResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;
    private final MessageSource messageSource;

    @PostMapping("/{medicineId}")
    public ResponseEntity<BaseResponse<BasketResponse>> add(
            @PathVariable Long medicineId)
            throws NotFoundException, AlreadyExistException {
        BasketResponse basket = basketService.add(medicineId);

        BaseResponse<BasketResponse> baseResponse = BaseResponse.<BasketResponse>builder().data(basket).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PatchMapping("/{basketId}")
    public ResponseEntity<BaseResponse<BasketResponse>> update(
            @PathVariable Long basketId,
            @RequestBody UpdateBasketRequest request)
            throws NotFoundException, IllegalOperationException {

        BasketResponse basket = basketService.update(basketId, request);

        BaseResponse<BasketResponse> baseResponse = BaseResponse.<BasketResponse>builder().data(basket).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @DeleteMapping("/{basketId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long basketId)
            throws NotFoundException, IllegalOperationException {
        basketService.remove(basketId);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(
                messageSource.getMessage("basket.deleteMedicine", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> delete() throws NotFoundException {
        basketService.removeByLoggedInUser();

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(
                messageSource.getMessage("basket.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @PostMapping("/bought")
    public ResponseEntity<BaseResponse<Void>> bought() throws NotFoundException, IOException {
        basketService.bought();

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(
                messageSource.getMessage("basket.bought", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<FullBasketResponse>> getByUser() throws NotFoundException {
        FullBasketResponse fullBasketResponse = basketService.getByLoggedInUser();

        BaseResponse<FullBasketResponse> baseResponse = BaseResponse.<FullBasketResponse>builder()
                .data(fullBasketResponse).build();
        return ResponseEntity.ok(baseResponse);
    }
}
