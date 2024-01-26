package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.config.SecurityConfig;
import az.matrix.aptek.ms.dao.MedicineDao;
import az.matrix.aptek.ms.dao.RatingDao;
import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.request.CreateRatingRequest;
import az.matrix.aptek.ms.dto.request.UpdateRatingRequest;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.dto.response.RatingResponse;
import az.matrix.aptek.ms.entity.Rating;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.RatingMapper;
import az.matrix.aptek.ms.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingDao ratingDao;
    private final MessageSource messageSource;
    private final SecurityConfig securityConfig;
    private final RatingMapper ratingMapper;
    private final UserDao userDao;
    private final MedicineDao medicineDao;

    @Override
    public RatingResponse add(Long medicineId, CreateRatingRequest createRating)
            throws AlreadyExistException, IllegalOperationException, NotFoundException {
        Rating rating = ratingMapper.map(createRating);
        rating.setMedicine(medicineDao.getById(medicineId));
        rating.setUser(userDao.getLoggedInUser());

        checkBound(rating);
        checkExist(rating);
        rating.setCreatedAt(new Date());

        rating = ratingDao.add(rating);

        return ratingMapper.map(rating);
    }

    @Override
    public RatingResponse update(Long ratingId, UpdateRatingRequest updateRating) throws NotFoundException {
        Rating rating = ratingDao.getById(ratingId);
        ratingMapper.map(updateRating, rating);

        rating = ratingDao.update(rating);
        return ratingMapper.map(rating);
    }

    @Override
    public void remove(Long id) throws NotFoundException, IllegalOperationException {
        checkPermission(id);

        ratingDao.remove(id);
    }

    @Override
    public PageResponse<RatingResponse> getRatings(Long medicineId, Integer page, Integer limit) {
        Page<Rating> ratings = ratingDao.getRatings(medicineId, page, limit);

        return ratingMapper.map(ratings);
    }

    private void checkExist(Rating rating) throws AlreadyExistException {
        try {
            Rating foundRating = ratingDao.getByUserIdAndMedicineId(rating.getUser().getId(),
                    rating.getMedicine().getId());

            if (foundRating != null && (rating.getId() == null || !foundRating.getId().equals(rating.getId())))
                throw new AlreadyExistException(messageSource.getMessage("rating.exist", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }

    private void checkPermission(Long id) throws IllegalOperationException {
        try {
            if (!ratingDao.getById(id).getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
                throw new IllegalOperationException(messageSource.getMessage("rating.forbiddenDelete", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }

    private void checkBound(Rating rating) throws IllegalOperationException {
        if (rating.getRating() < 0 || rating.getRating() > 5)
            throw new IllegalOperationException(messageSource.getMessage("rating.outOfBound", null,
                    LocaleContextHolder.getLocale()));
    }
}
