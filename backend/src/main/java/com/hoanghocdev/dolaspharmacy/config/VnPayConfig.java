package com.hoanghocdev.dolaspharmacy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class VnPayConfig {
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";
    public static String orderType = "other";

    @Value("${vnpay.returnUrl}")
    public String vnp_ReturnUrl;

    @Value("${vnpay.tmnCode}")
    public String vnp_TmnCode;

    @Value("${vnpay.secretKey}")
    public String secretKey;

    public String getReturnUrl() {
        return vnp_ReturnUrl;
    }

    public String getTmnCode() {
        return vnp_TmnCode;
    }

    public String getSecretKey(){
        return secretKey;
    }



}
