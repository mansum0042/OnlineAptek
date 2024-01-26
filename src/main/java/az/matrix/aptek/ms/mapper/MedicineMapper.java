package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.CreateMedicineRequest;
import az.matrix.aptek.ms.dto.request.UpdateMedicineRequest;
import az.matrix.aptek.ms.dto.response.MedicineResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.entity.Medicine;
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
public interface MedicineMapper {
    MedicineMapper MEDICINE_MAPPER = Mappers.getMapper(MedicineMapper.class);

    Medicine map(CreateMedicineRequest source);

    @Mapping(source = "ratings", target = "avgRating", qualifiedByName = "toAvgRating")
    MedicineResponse map(Medicine source);

    List<MedicineResponse> map(List<Medicine> source);

    void map(UpdateMedicineRequest source, @MappingTarget Medicine target);

    default PageResponse<MedicineResponse> map(Page<Medicine> source) {
        PageResponse<MedicineResponse> pageResponse = new PageResponse<>();
        List<MedicineResponse> medicineResponses = new ArrayList<>();

        source.forEach(medicine -> medicineResponses.add(MEDICINE_MAPPER.map(medicine)));
        pageResponse.setData(medicineResponses);
        pageResponse.setPageNo(source.getNumber() + 1);
        pageResponse.setPageSize(source.getSize());
        pageResponse.setTotalPages(source.getTotalPages());

        return pageResponse;
    }

    @Named("toAvgRating")
    default Float toAvgRating(List<Rating> ratings) {
        if (ratings == null)
            return 0.0F;
        float rating = 0.0F;
        int count = 0;
        for (Rating ratingObj : ratings) {
            rating += ratingObj.getRating();
            count++;
        }
        return count != 0 ? rating / count : 0;
    }
}
