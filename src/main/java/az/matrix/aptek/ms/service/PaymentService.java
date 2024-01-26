package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.response.FullBasketResponse;

import java.io.IOException;

public interface PaymentService {
    void sendPurchaseFiche(String email, FullBasketResponse fullBasketResponse) throws IOException;
}
