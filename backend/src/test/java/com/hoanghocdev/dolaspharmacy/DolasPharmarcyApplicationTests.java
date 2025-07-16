package com.hoanghocdev.dolaspharmacy;

import com.hoanghocdev.dolaspharmacy.service.ProductService;
import com.hoanghocdev.dolaspharmacy.service.impl.ProductServiceImpl;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DolasPharmarcyApplicationTests {

    @Autowired
    ProductService productService;

    @Test
    void nothing() {
        System.out.println(productService.findProductBySlug("").getId());
    }
}
