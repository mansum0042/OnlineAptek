package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.CreateBranchRequest;
import az.matrix.aptek.ms.dto.request.UpdateBranchRequest;
import az.matrix.aptek.ms.dto.response.BranchResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.entity.Branch;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BranchMapper {
    BranchMapper BRANCH_MAPPER = Mappers.getMapper(BranchMapper.class);

    Branch map(CreateBranchRequest source);

    BranchResponse map(Branch source);

    void map(UpdateBranchRequest source, @MappingTarget Branch target);

    default PageResponse<BranchResponse> map(Page<Branch> source) {
        PageResponse<BranchResponse> pageResponse = new PageResponse<>();
        List<BranchResponse> branchResponses = source.stream().map(BRANCH_MAPPER::map).toList();

        pageResponse.setData(branchResponses);
        pageResponse.setPageNo(source.getNumber() + 1);
        pageResponse.setPageSize(source.getSize());
        pageResponse.setTotalPages(source.getTotalPages());

        return pageResponse;
    }
}
