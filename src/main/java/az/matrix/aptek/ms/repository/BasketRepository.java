package az.matrix.aptek.ms.repository;

import az.matrix.aptek.ms.entity.Basket;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUser(User user);

    Optional<Basket> findByUserAndMedicine(User user, Medicine medicine);

    @Transactional
    void deleteAllByUser (User user);
}
