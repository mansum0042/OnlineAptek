package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.BranchDao;
import az.matrix.aptek.ms.entity.Branch;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchDaoJpaImpl implements BranchDao {
    private final BranchRepository branchRepository;
    private final MessageSource messageSource;

    @Override
    public Branch add(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch update(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public void remove(Branch branch) throws NotFoundException {
        branchRepository.delete(branch);
    }

    @Override
    public Branch getById(Long id) throws NotFoundException {
        return branchRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("branch.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Branch getByUser(User user) throws NotFoundException {
        return branchRepository.findByUser(user).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("branch.notFoundByUser", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Page<Branch> getAll(Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return branchRepository.findAll(pageable);
    }

    @Override
    public Branch getByName(String name) throws NotFoundException {
        return branchRepository.findByName(name).orElseThrow(NotFoundException::new);
    }
}
