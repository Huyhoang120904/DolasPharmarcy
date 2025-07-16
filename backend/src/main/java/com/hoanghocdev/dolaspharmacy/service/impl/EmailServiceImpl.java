package com.hoanghocdev.dolaspharmacy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanghocdev.dolaspharmacy.entity.Order;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmailServiceImpl {

    SendGrid sendGrid;
    ObjectMapper objectMapper;

    @NonFinal
    @Value("${mail.sendgrid.fromEmail}")
    String fromEmail;

    @NonFinal
    @Value("${mail.sendgrid.orderConfirmationTemplateId}")
    String orderConfirmationTemplateId;

    public void sendOrderConfirmationMail(Order order) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(new Email(fromEmail));
        mail.setTemplateId(orderConfirmationTemplateId);

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(order.getUserDetail().getEmail()));

        Map<String,Object> orderJsonData = objectMapper.convertValue(order, Map.class);

        orderJsonData.entrySet().forEach(entrySet ->
                personalization.addDynamicTemplateData(entrySet.getKey(), entrySet.getValue()));

        mail.addPersonalization(personalization);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setBody(mail.build());
        request.setEndpoint("mail/send");

        Response response = sendGrid.api(request);

        log.info("Email response: {}", response.getStatusCode());
        log.info("Email response: {}", response.getBody());
        log.info("Email response: {}", response.getHeaders());


        if(response.getStatusCode() != 202 ) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

}
