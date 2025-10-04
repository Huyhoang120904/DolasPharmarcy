package com.hoanghocdev.dolaspharmacy.service.impl;

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
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmailServiceImpl {

    SendGrid sendGrid;

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

        // Build custom orderJsonData for SendGrid template - all values must be primitives
        Map<String, Object> orderJsonData = new java.util.HashMap<>();
        orderJsonData.put("id", order.getId());
        orderJsonData.put("totalAmount", order.getTotal());
        // Convert LocalDate to string format for JSON serialization
        orderJsonData.put("receiveDate", order.getReceiveDate() != null ?
            order.getReceiveDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
        orderJsonData.put("receiveTime", order.getReceiveTime());

        // Map payment method to template expected values
        String mappedPaymentMethod = mapPaymentMethod(order.getPaymentMethod().name());
        orderJsonData.put("paymentMethod", mappedPaymentMethod);
        orderJsonData.put("fullName", order.getFullName());

        // User detail - flatten to primitives only
        Map<String, Object> userDetail = new java.util.HashMap<>();
        if (order.getUserDetail() != null) {
            userDetail.put("email", order.getUserDetail().getEmail());
            userDetail.put("fullName", order.getUserDetail().getFullName());
        }
        orderJsonData.put("userDetail", userDetail);

        // Address - flatten to primitives only
        Map<String, Object> address = new java.util.HashMap<>();
        if (order.getAddress() != null) {
            address.put("address", order.getAddress().getAddress());
            address.put("ward", order.getAddress().getWard());
            address.put("district", order.getAddress().getDistrict());
            address.put("province", order.getAddress().getProvince());
            address.put("phoneNumber", order.getAddress().getPhoneNumber());
        }
        orderJsonData.put("address", address);

        // Order items - flatten completely to primitives
        java.util.List<java.util.Map<String, Object>> orderItems = new java.util.ArrayList<>();
        if (order.getOrderItems() != null) {
            for (var item : order.getOrderItems()) {
                java.util.Map<String, Object> itemMap = new java.util.HashMap<>();
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("finalPrice", item.getFinalPrice());

                // Variant - primitives only
                java.util.Map<String, Object> variant = new java.util.HashMap<>();
                if (item.getVariant() != null) {
                    variant.put("name", item.getVariant().getName());

                    // Product - primitives only
                    java.util.Map<String, Object> product = new java.util.HashMap<>();
                    var prod = item.getVariant().getProduct();
                    if (prod != null) {
                        product.put("productName", prod.getProductName());

                        // Images - ensure proper structure for template access
                        java.util.List<java.util.Map<String, Object>> images = new java.util.ArrayList<>();
                        String primaryImageUrl = null;

                        if (prod.getImages() != null && !prod.getImages().isEmpty()) {
                            for (var img : prod.getImages()) {
                                if (img != null && img.getUrl() != null) {
                                    java.util.Map<String, Object> imgMap = new java.util.HashMap<>();
                                    imgMap.put("url", img.getUrl());
                                    images.add(imgMap);

                                    // Set the first image as primary image for easier template access
                                    if (primaryImageUrl == null) {
                                        primaryImageUrl = img.getUrl();
                                    }


                                }
                            }
                        }

                        // If no images were added, use placeholder
                        if (images.isEmpty()) {
                            primaryImageUrl = "https://res.cloudinary.com/dbmtxumro/image/upload/v1746693032/Logo_dhixvq.png";
                            java.util.Map<String, Object> placeholderImg = new java.util.HashMap<>();
                            placeholderImg.put("url", primaryImageUrl);
                            images.add(placeholderImg);
                  
                        }

                        product.put("images", images);
                        product.put("primaryImageUrl", primaryImageUrl); // Direct access for template
                        

                        // Promotion - primitives only
                        if (prod.getPromotion() != null) {
                            java.util.Map<String, Object> promotion = new java.util.HashMap<>();
                            promotion.put("discountAmount", prod.getPromotion().getDiscountAmount());
                            product.put("promotion", promotion);
                        }
                    }
                    variant.put("product", product);
                }
                itemMap.put("variant", variant);
                orderItems.add(itemMap);
            }
        }
        orderJsonData.put("orderItems", orderItems);

        // Log the complete order items structure for debugging

        for (int i = 0; i < orderItems.size(); i++) {
            var item = orderItems.get(i);

        }

        // Add each key-value pair individually to avoid object serialization issues
        for (Map.Entry<String, Object> entry : orderJsonData.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());

        }

        mail.addPersonalization(personalization);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setBody(mail.build());
        request.setEndpoint("mail/send");

        Response response = sendGrid.api(request);


        if (response.getStatusCode() != 202) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    /**
     * Map internal payment method values to template expected values
     */
    private String mapPaymentMethod(String internalPaymentMethod) {
        return switch (internalPaymentMethod) {
            case "CASH_ON_DELIVERY", "COD" -> "CASH";
            case "E_BANKING", "ONLINE_BANKING" -> "E_BANKING";
            case "CREDIT_CARD", "CARD" -> "CREDIT_CARD";
            default -> internalPaymentMethod;
        };
    }
}
