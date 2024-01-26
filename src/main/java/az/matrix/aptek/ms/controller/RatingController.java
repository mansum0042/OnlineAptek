package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.CreateRatingRequest;
import az.matrix.aptek.ms.dto.request.UpdateRatingRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.dto.response.RatingResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final MessageSource messageSource;

    @PostMapping("/{medicineId}")
    public ResponseEntity<BaseResponse<RatingResponse>> add(
            @PathVariable Long medicineId,
            @RequestBody @Valid CreateRatingRequest request)
            throws AlreadyExistException, NotFoundException, IllegalOperationException {
        RatingResponse ratingResponse = ratingService.add(medicineId, request);

        BaseResponse<RatingResponse> baseResponse = BaseResponse.<RatingResponse>builder().data(ratingResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<RatingResponse>> update(
            @PathVariable Long id,
            @RequestBody UpdateRatingRequest createRatingRequest)
            throws NotFoundException {
        RatingResponse ratingResponse = ratingService.update(id, createRatingRequest);

        BaseResponse<RatingResponse> baseResponse = BaseResponse.<RatingResponse>builder().data(ratingResponse).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @PathVariable Long id) throws NotFoundException, IllegalOperationException {
        ratingService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(messageSource.getMessage(
                "rating.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @GetMapping("/medicine/{medicineId}")
    @SuppressWarnings("Duplicates")
    public ResponseEntity<PageResponse<RatingResponse>> getAll(
            @PathVariable Long medicineId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<RatingResponse> ratings = ratingService.getRatings(medicineId, page, limit);
        return ResponseEntity.ok(ratings);
    }
}
