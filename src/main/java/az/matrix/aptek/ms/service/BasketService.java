package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.UpdateBasketRequest;
import az.matrix.aptek.ms.dto.response.BasketResponse;
import az.matrix.aptek.ms.dto.response.FullBasketResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;

import java.io.IOException;

public interface BasketService {
    BasketResponse add(Long medicineId) throws AlreadyExistException, NotFoundException;

    BasketResponse update(Long basketId, UpdateBasketRequest updateBasket) throws IllegalOperationException, NotFoundException;

    void remove(Long id) throws IllegalOperationException, NotFoundException;

    void removeByLoggedInUser() throws NotFoundException;

    FullBasketResponse getByLoggedInUser() throws NotFoundException;

    void bought() throws NotFoundException, IOException;
}
