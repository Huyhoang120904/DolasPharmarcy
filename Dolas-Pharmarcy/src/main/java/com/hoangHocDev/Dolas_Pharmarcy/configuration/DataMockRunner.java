package com.hoangHocDev.Dolas_Pharmarcy.configuration;

import com.hoangHocDev.Dolas_Pharmarcy.entity.*;
import com.hoangHocDev.Dolas_Pharmarcy.entity.enums.ProductStatus;
import com.hoangHocDev.Dolas_Pharmarcy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DataMockRunner {

    CategoryRepository categoryRepository;
    SupplierRepository supplierRepository;
    TargetRepository targetRepository;
    PromotionRepository promotionRepository;
    ProductRepository productRepository;
    VariantRepository variantRepository;
    ImageRepository imageRepository;
    UserEntityRepository userEntityRepository;

    @Bean
    public CommandLineRunner mockData() {
        return args -> {
            Faker faker = new Faker();

            if (productRepository.count() > 0) {
                return ;
            }

            // --- Category ---
            Category category = Category.builder()

                    .categoryName("Painkillers")
                    .slug("painkillers")
                    .build();
            category = categoryRepository.save(category);

            // --- Supplier ---
            Supplier supplier = Supplier.builder()

                    .supplierName(faker.company().name())
                    .build();
            supplier = supplierRepository.save(supplier);

            // --- Target ---
            Target target = Target.builder()
                    .name("Adults")
                    .build();
            target = targetRepository.save(target);

            // --- Promotion ---
            Promotion promotion = Promotion.builder()

                    .promotionName("Summer Sale")
                    .discountAmount(10.0)
                    .build();
            promotion = promotionRepository.save(promotion);
            
            // --- 30 Products with DataFaker ---
            List<Product> productList = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                String productName = faker.commerce().productName();
                String sku = "SKU-" + String.format("%03d", i);
                String origin = faker.address().country();
                String warning = faker.lorem().sentence();
                String ingredients = faker.food().ingredient();
                String dosage = faker.number().numberBetween(10, 1000) + "mg";
                String description = faker.lorem().paragraph();
                String usageInstruction = "Use as directed. " + faker.lorem().sentence();
                String slug = productName.toLowerCase().replaceAll("[^a-z0-9]+", "-") + "-" + i;
                boolean requiresPrescription = i % 3 == 0;
                ProductStatus status = ProductStatus.ACTIVE;

                Product product = Product.builder()
                        .productName(productName)
                        .sku(sku)
                        .origin(origin)
                        .warning(warning)
                        .ingredients(ingredients)
                        .dosage(dosage)
                        .description(description)
                        .usageInstruction(usageInstruction)
                        .slug(slug)
                        .requiresPrescription(requiresPrescription)
                        .productStatus(status)
                        .supplier(supplier)
                        .target(target)
                        .category(category)
                        .promotion(promotion)
                        .build();
                product = productRepository.save(product);

                // --- Variant for each product ---
                Variant variant = Variant.builder()
                        .name("Box of " + faker.number().numberBetween(10, 50))
                        .price(faker.number().randomDouble(2, 5, 100))
                        .stock(faker.number().numberBetween(10, 200))
                        .product(product)
                        .build();

                product.setVariants(List.of(variant));
                productRepository.save(product);

                productList.add(product);
            }

            // --- UserEntity example ---
            UserEntity user = UserEntity.builder()
                    .username("john_doe")
                    .password("password") // In production, always store hashed passwords!
                    .build();
            userEntityRepository.save(user);

            // Add other entities as needed (Order, Cart, Favourites, etc.)
        };
    }
}