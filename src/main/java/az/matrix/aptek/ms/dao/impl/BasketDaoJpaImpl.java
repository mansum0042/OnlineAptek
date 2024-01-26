package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.BasketDao;
import az.matrix.aptek.ms.entity.Basket;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketDaoJpaImpl implements BasketDao {
    private final BasketRepository basketRepository;
    private final MessageSource messageSource;

    @Override
    public Basket add(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public Basket update(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public void remove(Long id) {
        basketRepository.deleteById(id);
    }

    @Override
    public void removeAllByUser(User user) {
        basketRepository.deleteAllByUser(user);
    }

    @Override
    public List<Basket> getByUser(User user) {
        return basketRepository.findByUser(user);
    }

    @Override
    public Basket getById(Long id) throws NotFoundException {
        return basketRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("basket.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Basket getByUserAndMedicine(User user, Medicine medicine) throws NotFoundException {
        return basketRepository.findByUserAndMedicine(user, medicine).orElseThrow(NotFoundException::new);
    }
}
