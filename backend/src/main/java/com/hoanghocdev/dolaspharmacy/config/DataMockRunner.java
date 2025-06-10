package com.hoanghocdev.dolaspharmacy.config;

import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import com.hoanghocdev.dolaspharmacy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DataMockRunner {

    RoleRepository roleRepository;
    CategoryRepository categoryRepository;
    SupplierRepository supplierRepository;
    TargetRepository targetRepository;
    PromotionRepository promotionRepository;
    ProductRepository productRepository;
    UserEntityRepository userEntityRepository;
    VariantRepository variantRepository;
    BrandRepository brandRepository;

    @Bean
    public CommandLineRunner mockData() {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();

            // Avoid duplicate mock
            if (productRepository.count() > 0) {
                return;
            }

            // --- Categories ---
            List<Category> categories = Arrays.asList(
                    Category.builder().categoryName("Quà Tặng Sức Khỏe").description("Quà tặng liên quan đến sức khỏe").slug("qua-tang-suc-khoe").isActive(true).build(),
                    Category.builder().categoryName("Thiết Bị Y Tế").description("Các thiết bị y tế và dụng cụ chăm sóc sức khỏe").slug("thiet-bi-y-te").isActive(true).build(),
                    Category.builder().categoryName("Khuyến Mãi Hot").description("Các sản phẩm đang có khuyến mãi hấp dẫn").slug("khuyen-mai-hot").isActive(true).build(),
                    Category.builder().categoryName("Vitamin & Khoáng Chất").description("Sản phẩm vitamin và khoáng chất bổ sung").slug("vitamin-khoang-chat").isActive(true).build(),
                    Category.builder().categoryName("Vitamin Cho U50+").description("Vitamin dành cho người trên 50 tuổi").slug("vitamin-cho-u50").isActive(true).build(),
                    Category.builder().categoryName("Vitamin Cho Mẹ").description("Vitamin dành cho phụ nữ mang thai và cho con bú").slug("vitamin-cho-me").isActive(true).build(),
                    Category.builder().categoryName("Dưỡng Trắng Da").description("Sản phẩm dưỡng trắng và chăm sóc da").slug("duong-trang-da").isActive(true).build(),
                    Category.builder().categoryName("Ung Thư - Bướu").description("Sản phẩm hỗ trợ cho người ung thư, bướu").slug("ung-thu-buou").isActive(true).build()
            );
            categories = categoryRepository.saveAll(categories);

            // --- Suppliers ---
            List<Supplier> suppliers = IntStream.range(0, 5)
                    .mapToObj(i -> Supplier.builder().supplierName(faker.company().name()).build())
                    .collect(Collectors.toList());
            suppliers = supplierRepository.saveAll(suppliers);

            // --- Targets ---
            List<String> targetNames = Arrays.asList("Adults", "Children", "Elderly", "Pregnant Women", "Athletes");
            List<Target> targets = targetNames.stream()
                    .map(name -> Target.builder().name(name).build())
                    .collect(Collectors.toList());
            targets = targetRepository.saveAll(targets);

            // --- Promotions ---
            List<Promotion> promotions = Arrays.asList(
                    Promotion.builder().promotionName("Summer Sale").discountAmount(10.0).build(),
                    Promotion.builder().promotionName("Back to School").discountAmount(15.0).build(),
                    Promotion.builder().promotionName("Flash Sale").discountAmount(20.0).build(),
                    Promotion.builder().promotionName("New Year Sale").discountAmount(5.0).build()
            );
            promotions = promotionRepository.saveAll(promotions);
            List<Promotion> availablePromotions = new ArrayList<>(promotions);

            // --- Brands ---
            Set<String> brandNames = new HashSet<>(Arrays.asList(
                    "TC Pharma", "Sebamed", "La Beauté", "Fobelife", "NeoAqua", "Kutieskin", "VITADAIRY",
                    "Good Health", "Blackmores", "KenKan", "Pharma World", "Vitabiotics", "OCAVILL",
                    "Phytextra", "OMEXXEL", "Vitatree", "Livespo", "Traphaco", "Vesta", "Jpanwell",
                    "Vitamins For Life", "Pharvina", "GINIC"
            ));

            List<Brand> brands = brandNames.stream()
                    .filter(brandName -> !brandRepository.existsById(brandName)) // Avoid duplicates in DB
                    .map(name -> Brand.builder()
                            .brandName(name)
                            .origin(faker.address().country())
                            .build())
                    .collect(Collectors.toList());
            brands = brandRepository.saveAll(brands);

            // --- Products ---
            int productCount = 40;
            for (int i = 1; i <= productCount; i++) {
                String productName = faker.commerce().productName() + " " + faker.number().digits(2);
                String sku = "SKU-" + String.format("%04d", i);
                String origin = faker.address().country();
                String warning = faker.lorem().sentence();
                String ingredients = faker.food().ingredient() + ", " + faker.food().ingredient();
                String dosage = faker.number().numberBetween(10, 1000) + "mg";
                String description = faker.lorem().paragraph(2);
                String usageInstruction = "Use as directed. " + faker.lorem().sentence();
                String slug = productName.toLowerCase().replaceAll("[^a-z0-9]+", "-") + "-" + sku.toLowerCase();
                boolean requiresPrescription = i % 4 == 0;
                ProductStatus status = (i % 7 == 0) ? ProductStatus.OUT_OF_STOCK : ProductStatus.ACTIVE;

                Category randomCategory = categories.get(random.nextInt(categories.size()));
                Supplier randomSupplier = suppliers.get(random.nextInt(suppliers.size()));
                Target randomTarget = targets.get(random.nextInt(targets.size()));
                Brand randomBrand = brands.get(random.nextInt(brands.size()));

                Promotion assignedPromotion = null;
                if (!availablePromotions.isEmpty() && random.nextDouble() < 0.6) {
                    assignedPromotion = availablePromotions.remove(0);
                }

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
                        .supplier(randomSupplier)
                        .target(randomTarget)
                        .category(randomCategory)
                        .brand(randomBrand)
                        .promotion(assignedPromotion)
                        .build();
                product = productRepository.save(product);
            }
        };
    }
}