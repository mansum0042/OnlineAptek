package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.config.SecurityConfig;
import az.matrix.aptek.ms.dao.BasketDao;
import az.matrix.aptek.ms.dao.MedicineDao;
import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.request.UpdateBasketRequest;
import az.matrix.aptek.ms.dto.response.BasketResponse;
import az.matrix.aptek.ms.dto.response.FullBasketResponse;
import az.matrix.aptek.ms.entity.Basket;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.BasketMapper;
import az.matrix.aptek.ms.service.BasketService;
import az.matrix.aptek.ms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketDao basketDao;
    private final MessageSource messageSource;
    private final SecurityConfig securityConfig;
    private final UserDao userDao;
    private final MedicineDao medicineDao;
    private final BasketMapper basketMapper;
    private final PaymentService paymentService;

    @Override
    public BasketResponse add(Long medicineId) throws AlreadyExistException, NotFoundException {
        User user = userDao.getLoggedInUser();
        Medicine medicine = medicineDao.getById(medicineId);

        Basket basket = Basket.builder()
                .user(user)
                .medicine(medicine)
                .createdAt(new Date())
                .quantity(1)
                .build();

        checkMedicineExist(basket);

        return basketMapper.map(basketDao.add(basket));
    }

    @Override
    public BasketResponse update(Long basketId, UpdateBasketRequest updateBasket)
            throws IllegalOperationException, NotFoundException {
        Basket basket = basketDao.getById(basketId);
        basketMapper.map(updateBasket, basket);

        if (!basket.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("basket.forbiddenUpdate", null,
                    LocaleContextHolder.getLocale()));
        if (basket.getQuantity() > basket.getMedicine().getQuantity())
            throw new IllegalOperationException(messageSource.getMessage("basket.outOfStock", null,
                    LocaleContextHolder.getLocale()));

        return basketMapper.map(basketDao.update(basket));
    }

    @Override
    public void remove(Long id) throws IllegalOperationException, NotFoundException {
        Basket basket = basketDao.getById(id);
        if (!basket.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("basket.forbiddenDelete", null,
                    LocaleContextHolder.getLocale()));
        basketDao.remove(id);
    }

    @Override
    public void removeByLoggedInUser() throws NotFoundException {
        User user = userDao.getLoggedInUser();
        basketDao.removeAllByUser(user);
    }

    @Override
    public FullBasketResponse getByLoggedInUser() throws NotFoundException {
        User user = userDao.getLoggedInUser();
        List<Basket> baskets = basketDao.getByUser(user);
        return basketMapper.mapToFullBasketResponse(baskets);
    }

    @Override
    public void bought() throws NotFoundException, IOException {
        User user = userDao.getLoggedInUser();

        List<Basket> baskets = basketDao.getByUser(user);
        FullBasketResponse fullBasketResponse = getByLoggedInUser();
        paymentService.sendPurchaseFiche(user.getEmail(), fullBasketResponse);

        //decreasing medicine quantities from branches
        for (Basket basket : baskets) {
            Medicine medicine = basket.getMedicine();
            medicine.setQuantity(medicine.getQuantity() - basket.getQuantity());
            medicineDao.update(medicine);
        }
        removeByLoggedInUser();
    }

    private void checkMedicineExist(Basket basket) throws AlreadyExistException {
        try {
            Basket foundBasket = basketDao.getByUserAndMedicine(basket.getUser(), basket.getMedicine());
            if (foundBasket != null && (basket.getId() == null || !foundBasket.getId().equals(basket.getId())))
                throw new AlreadyExistException(messageSource.getMessage("basket.existByUserAndMedicine",
                        null, LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }
}
