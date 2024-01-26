package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.entity.Basket;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.NotFoundException;

import java.util.List;

public interface BasketDao {
    Basket add(Basket basket);

    Basket update(Basket basket);

    void remove(Long id);

    void removeAllByUser(User user);

    List<Basket> getByUser(User user);

    Basket getById(Long id) throws NotFoundException;

    Basket getByUserAndMedicine(User user, Medicine medicine) throws NotFoundException;
}
