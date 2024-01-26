package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.UpdateBasketRequest;
import az.matrix.aptek.ms.dto.response.BasketResponse;
import az.matrix.aptek.ms.dto.response.FullBasketResponse;
import az.matrix.aptek.ms.entity.Basket;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BasketMapper {
    BasketMapper INSTANCE = Mappers.getMapper(BasketMapper.class);

    BasketResponse map(Basket source);

    List<BasketResponse> map(List<Basket> source);

    void map(UpdateBasketRequest source, @MappingTarget Basket target);

    @Named("mapToFullBasketResponse")
    default FullBasketResponse mapToFullBasketResponse(List<Basket> source) {
        List<BasketResponse> basketResponses = INSTANCE.map(source);
        int quantitySum = 0;
        float priceSum = 0F;
        for (BasketResponse basket : basketResponses) {
            quantitySum += basket.getQuantity();
            priceSum += basket.getQuantity() * basket.getMedicine().getPrice();
        }
        return FullBasketResponse.builder()
                .baskets(basketResponses)
                .quantity(quantitySum)
                .price(priceSum)
                .build();
    }
}
