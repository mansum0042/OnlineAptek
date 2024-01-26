package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.CreateBranchRequest;
import az.matrix.aptek.ms.dto.request.UpdateBranchRequest;
import az.matrix.aptek.ms.dto.response.BranchResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.entity.Branch;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;

public interface BranchService {
    BranchResponse add(CreateBranchRequest createBranch) throws AlreadyExistException, NotFoundException;

    BranchResponse update(UpdateBranchRequest updateBranch) throws AlreadyExistException, NotFoundException;

    void remove() throws NotFoundException;

    BranchResponse getById(Long id) throws NotFoundException;

    PageResponse<BranchResponse> getAll(Integer page, Integer limit);
}
