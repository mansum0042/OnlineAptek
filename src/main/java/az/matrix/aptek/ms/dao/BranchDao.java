package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.entity.Branch;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface BranchDao {
    Branch add(Branch branch);

    Branch update(Branch branch);

    void remove(Branch branch) throws NotFoundException;

    Branch getById(Long id) throws NotFoundException;

    Branch getByUser(User user) throws NotFoundException;

    Page<Branch> getAll(Integer page, Integer limit);

    Branch getByName(String name) throws NotFoundException;
}
