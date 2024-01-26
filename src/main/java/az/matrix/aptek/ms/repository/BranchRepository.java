package az.matrix.aptek.ms.repository;

import az.matrix.aptek.ms.entity.Branch;
import az.matrix.aptek.ms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);

    Optional<Branch> findByUser(User user);
}
