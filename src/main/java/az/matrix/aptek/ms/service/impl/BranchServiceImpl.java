package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.dao.BranchDao;
import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.request.CreateBranchRequest;
import az.matrix.aptek.ms.dto.request.UpdateBranchRequest;
import az.matrix.aptek.ms.dto.response.BranchResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.entity.Branch;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.BranchMapper;
import az.matrix.aptek.ms.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchDao branchDao;
    private final MessageSource messageSource;
    private final BranchMapper branchMapper;
    private final UserDao userDao;

    @Override
    public BranchResponse add(CreateBranchRequest createBranch) throws AlreadyExistException, NotFoundException {
        Branch branch = branchMapper.map(createBranch);

        User user = userDao.getLoggedInUser();
        branch.setUser(user);

        checkBranchExistByUser(branch);
        checkNameExist(branch);

        return branchMapper.map(branchDao.add(branch));
    }

    @Override
    public BranchResponse update(UpdateBranchRequest updateBranch) throws AlreadyExistException, NotFoundException {
        User user = userDao.getLoggedInUser();
        Branch branch = branchDao.getByUser(user);
        branchMapper.map(updateBranch, branch);

        checkNameExist(branch);

        return branchMapper.map(branchDao.update(branch));
    }

    @Override
    public void remove() throws NotFoundException {
        User user = userDao.getLoggedInUser();
        Branch branch = branchDao.getByUser(user);

        branchDao.remove(branch);
    }

    @Override
    public BranchResponse getById(Long id) throws NotFoundException {
        Branch branch = branchDao.getById(id);

        return branchMapper.map(branch);
    }

    @Override
    public PageResponse<BranchResponse> getAll(Integer page, Integer limit) {
        Page<Branch> branches = branchDao.getAll(page, limit);

        return branchMapper.map(branches);
    }

    private void checkNameExist(Branch branch) throws AlreadyExistException {
        try {
            Branch foundBranch = branchDao.getByName(branch.getName());
            if (foundBranch != null && (branch.getId() == null || !foundBranch.getId().equals(branch.getId())))
                throw new AlreadyExistException(messageSource.getMessage("branch.existByName", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }

    private void checkBranchExistByUser(Branch branch) throws AlreadyExistException {
        try {
            branchDao.getByUser(branch.getUser());
            throw new AlreadyExistException(messageSource
                    .getMessage("branch.existByUser", null, LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }
}
