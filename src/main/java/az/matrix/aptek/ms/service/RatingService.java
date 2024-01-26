package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.CreateRatingRequest;
import az.matrix.aptek.ms.dto.request.UpdateRatingRequest;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.dto.response.RatingResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;

public interface RatingService {
    RatingResponse add(Long medicineId, CreateRatingRequest createRating) throws AlreadyExistException, IllegalOperationException, NotFoundException;

    RatingResponse update(Long ratingId, UpdateRatingRequest updateRating) throws NotFoundException;

    void remove(Long id) throws NotFoundException, IllegalOperationException;

    PageResponse<RatingResponse> getRatings(Long medicineId, Integer page, Integer limit);
}
