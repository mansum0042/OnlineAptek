package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.dto.response.FullBasketResponse;
import az.matrix.aptek.ms.service.EmailService;
import az.matrix.aptek.ms.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final EmailService emailService;

    @Value("classpath:templates/purchase-invoice-template.html")
    private Resource template;

    @Value("classpath:templates/purchase-invoice-medicine-row.html")
    private Resource row;


    @Override
    public void sendPurchaseFiche(String email, FullBasketResponse fullBasketResponse) throws IOException {
        Reader reader = new InputStreamReader(template.getInputStream(), StandardCharsets.UTF_8);
        String template = FileCopyUtils.copyToString(reader);
        template = template.replace("#rows", getRows(fullBasketResponse));
        template = template.replace("#total", fullBasketResponse.getPrice().toString());

        emailService.sendEmail(new String[]{email}, "Purchase fiche", template);
    }

    private String getRows(FullBasketResponse fullBasketResponse) throws IOException {
        Reader reader = new InputStreamReader(row.getInputStream(), StandardCharsets.UTF_8);
        String row = FileCopyUtils.copyToString(reader);

        StringBuilder rows = new StringBuilder();

        fullBasketResponse.getBaskets().forEach(basket ->
        {
            String temp = row.replace("#medicine-name", basket.getMedicine().getMedicineName())
                    .replace("#medicine-quantity", basket.getQuantity().toString())
                    .replace("#medicine-price", basket.getMedicine().getPrice().toString())
                    .replace("#medicine-total", String.valueOf(basket.getQuantity() * basket.getMedicine().getPrice()));
            rows.append(temp);
        });

        return rows.toString();
    }
}
