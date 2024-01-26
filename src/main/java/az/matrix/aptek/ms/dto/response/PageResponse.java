package az.matrix.aptek.ms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> extends BaseResponse<List<T>> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
}
