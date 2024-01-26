package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.CreateRatingRequest;
import az.matrix.aptek.ms.dto.request.UpdateRatingRequest;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.dto.response.RatingResponse;
import az.matrix.aptek.ms.entity.Rating;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RatingMapper {
    RatingMapper RATING_MAPPER = Mappers.getMapper(RatingMapper.class);

    RatingResponse map(Rating source);

    Rating map(CreateRatingRequest source);

    void map(UpdateRatingRequest source, @MappingTarget Rating target);

    default PageResponse<RatingResponse> map(Page<Rating> source) {
        PageResponse<RatingResponse> pageResponse = new PageResponse<>();
        List<RatingResponse> ratingResponses = new ArrayList<>();

        source.forEach(rating -> ratingResponses.add(RATING_MAPPER.map(rating)));
        pageResponse.setData(ratingResponses);
        pageResponse.setPageNo(source.getNumber() + 1);
        pageResponse.setPageSize(source.getSize());
        pageResponse.setTotalPages(source.getTotalPages());

        return pageResponse;
    }
}
