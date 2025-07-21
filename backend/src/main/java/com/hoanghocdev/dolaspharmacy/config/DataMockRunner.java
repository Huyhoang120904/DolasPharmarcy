package com.hoanghocdev.dolaspharmacy.config;

import com.hoanghocdev.dolaspharmacy.dto.request.*;
import com.hoanghocdev.dolaspharmacy.entity.*;
import com.hoanghocdev.dolaspharmacy.entity.enums.ProductStatus;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.repository.*;
import com.hoanghocdev.dolaspharmacy.service.ProductService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import com.hoanghocdev.dolaspharmacy.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    UserEntityService userEntityService;
    ProductService productService;
    @Bean
    public CommandLineRunner mockData(PasswordEncoder passwordEncoder) {
        return args -> {
            Faker faker = new Faker(new Locale("vi"));
            Random random = new Random();

            // Avoid duplicate mock
            if (productRepository.count() > 0) {
                return;
            }


            // Cloudinary images list
            List<String> cloudinaryImages = Arrays.asList(
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00021988-anica-phytextra-60v-513_fc7gpe.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_vogxfu.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_1_a0fqlp.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031036-omexxel-ginkgo-120-2x15_hx0z2x.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00029929-maxpremum-naga-plus-200_uu8e9w.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/img-9003-7e22ddc19e_1_czsgxg.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09932-bc701e2141_y7evhl.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/hebe-tuyp-truoc-908f63e863_xzl4jv.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341488/dsc-00535-480fad02f8_1_mxnxvw.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00032817-blood-care-jpanwell-60v_ule2rv.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00036-f81526ba97_oojjx4.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00025-00386132d2_ryqm1b.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00502603-vien-uong-tang-cuong-ch_ytdohn.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341486/00503081-sap-duong-am-vaseline-r_xz0dfq.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00503066-son-duong-moi-sebamed-l_iuf3kn.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00032923-vien-uong-cai-thien-tim_m6kd4c.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502680-vien-uong-lam-dep-da-ch_we06np.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502416-blackmores-executive-b-4_fsoxxw.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00501988-sua-cho-benh-nhan-gan-f_pyyoou.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032918-glucosamine-and-chondro_xsbh2t.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032923-vien-uong-cai-thien-tim_1_b56yqn.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032884-okinawa-fucoidan-jpanwe_ohmdom.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00031920-top-grow-jpanwell-10-ch-3f81b1a4-df3b-41f3-869d-c64cb90506fa_qble7r.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500955-tra-nhan-sam-ko-ginseng_a9hd5l.png",
                    "https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500119-vien-uong-ho-tro-giam-n_fyciyl.png"
            );

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

            for (Category category : categories) {
                String imageUrl = cloudinaryImages.get(random.nextInt(cloudinaryImages.size()));
                Image image = Image.builder().url(imageUrl).build();
                category.setImage(image);
            }
            categories = categoryRepository.saveAll(categories);

            // --- Suppliers ---
            List<String> supplierNames = Arrays.asList(
                    "Công ty Dược Hậu Giang",
                    "Công ty Traphaco",
                    "Công ty Dược Sài Gòn",
                    "Công ty Dược OPC",
                    "Công ty Dược Mediplantex"
            );
            List<Supplier> suppliers = supplierNames.stream()
                    .map(name -> Supplier.builder().supplierName(name).build())
                    .collect(Collectors.toList());
            suppliers = supplierRepository.saveAll(suppliers);

            // --- Targets ---
            List<String> targetNames = Arrays.asList("Người lớn", "Trẻ em", "Người cao tuổi", "Phụ nữ mang thai", "Vận động viên");
            List<Target> targets = targetNames.stream()
                    .map(name -> Target.builder().targetName(name).build())
                    .collect(Collectors.toList());
            targets = targetRepository.saveAll(targets);

            // --- Promotions ---
            List<Promotion> promotions = Arrays.asList(
                    Promotion.builder().promotionName("Khuyến mãi hè").discountAmount(10.0).build(),
                    Promotion.builder().promotionName("Mua 1 tặng 1").discountAmount(15.0).build(),
                    Promotion.builder().promotionName("Giảm giá sốc cuối tuần").discountAmount(20.0).build(),
                    Promotion.builder().promotionName("Tết sale").discountAmount(5.0).build()
            );
            promotions = promotionRepository.saveAll(promotions);
            List<Promotion> availablePromotions = new ArrayList<>(promotions);

            // --- Brands ---
            Set<String> brandNames = new HashSet<>(Arrays.asList(
                    "Dược Hậu Giang", "Traphaco", "OPC", "Mediplantex", "Imexpharm", "Pymepharco", "Domesco",
                    "DHG Pharma", "Nam Dược", "Pharimexco", "Sao Thái Dương", "Vimedimex", "Mekophar",
                    "Vidipha", "Stada Việt Nam", "Savi Pharm", "Boston Việt Nam", "Công ty Dược Hà Nội"
            ));
            List<Brand> brands = brandNames.stream()
                    .filter(brandName -> !brandRepository.existsById(brandName))
                    .map(name -> Brand.builder()
                            .brandName(name)
                            .origin("Việt Nam")
                            .build())
                    .collect(Collectors.toList());
            brands = brandRepository.saveAll(brands);

            // --- Products ---
            int productCount = 40;
            List<String> productNames = Arrays.asList(
                    "Paracetamol 500mg", "Vitamin C 1000mg", "C sủi", "Dầu gió xanh", "Nước muối sinh lý",
                    "Siro ho Prospan", "Thuốc nhỏ mắt V.Rohto", "Kem chống nắng Anessa", "Sữa rửa mặt Senka",
                    "Kem đánh răng P/S", "Băng cá nhân Urgo", "Khẩu trang y tế", "Nước súc miệng Listerine",
                    "Thuốc đau dạ dày Yumangel", "Men tiêu hóa Bio-acimin", "Viên sủi Berocca",
                    "Thuốc bổ máu Ferrovit", "Thuốc cảm Coldi-B", "Thuốc dị ứng Telfast", "Thuốc ho Bảo Thanh"
            );
            for (int i = 1; i <= productCount; i++) {
                String productName = productNames.get(random.nextInt(productNames.size())) + " " + faker.number().digits(2);
                String sku = "SKU-" + String.format("%04d", i);
                String origin = "Việt Nam";
                String warning = "Đọc kỹ hướng dẫn sử dụng trước khi dùng.";
                String ingredients = "Thành phần: " + faker.food().ingredient() + ", " + faker.food().ingredient();
                String dosage = faker.number().numberBetween(10, 1000) + "mg";
                String description = "Sản phẩm " + productName + " giúp tăng cường sức khỏe. " + faker.lorem().sentence();
                String usageInstruction = "Dùng theo chỉ định của bác sĩ hoặc dược sĩ.";
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

                ProductCreationRequest product = ProductCreationRequest.builder()
                        .productName(productName)
                        .sku(sku)
                        .origin(origin)
                        .warning(warning)
                        .ingredients(ingredients)
                        .dosage(dosage)
                        .description(description)
                        .usageInstruction(usageInstruction)
                        .slug(SlugUtils.toSlug(productName))
                        .requiresPrescription(requiresPrescription)
                        .productStatus(status)
                        .supplierId(randomSupplier.getId())
                        .target(TargetRequest.builder().targetName(randomTarget.getTargetName()).build())
                        .categoryId(randomCategory.getId())
                        .brand(BrandRequest.builder().brandName(randomBrand.getBrandName()).build())
                        .promotion(assignedPromotion==null? null :PromotionRequest.builder().promotionType(assignedPromotion.getPromotionType())
                                .discountAmount(assignedPromotion.getDiscountAmount())
                                .build())
                        .build();

                // --- Variants: 1-3 per product ---
                int variantCount = 1 + random.nextInt(3);
                List<VariantRequest> variants = new ArrayList<>();
                for (int v = 0; v < variantCount; v++) {
                    int rawPrice = faker.number().numberBetween(10000, 5000000); // 10,000 - 500,000 VND
                    int roundedPrice = (rawPrice / 10000) * 10000;
                    VariantRequest variant = VariantRequest.builder()
                            .name("Hộp " + (10 + random.nextInt(40)) + " viên")
                            .price(roundedPrice)
                            .stock(faker.number().numberBetween(0, 300))
                            .isPrimary(variants.isEmpty())
                            .build();
                    variants.add(variant);
                }
                product.setVariants(variants);

                List<ImageRequest> images = new ArrayList<>();
                // --- Images: 1-2 per product, use cloudinary images ---
                int imageCount = 1 + random.nextInt(2);
                Set<Integer> usedIndices = new HashSet<>();
                for (int img = 0; img < imageCount; img++) {
                    int imgIdx;
                    do {
                        imgIdx = random.nextInt(cloudinaryImages.size());
                    } while (usedIndices.contains(imgIdx));
                    usedIndices.add(imgIdx);

                    ImageRequest image = ImageRequest.builder()
                            .url(cloudinaryImages.get(imgIdx))
                            .isPrimary(images.isEmpty())
                            .build();
                    images.add(image);
                }
                product.setImages(images);

                productService.addNewProduct(product);
            }

            //Roles
            if (roleRepository.findAll().isEmpty()) {
                Role admin = Role.builder().rolename("ADMIN").build();
                roleRepository.save(admin);

                Role user = Role.builder().rolename("USER").build();
                roleRepository.save(user);
            }

            Role admin = roleRepository.findByRolename("ADMIN");
            Set<Role> adminRole = new HashSet<>();
            adminRole.add(admin);

            Role user = roleRepository.findByRolename("USER");
            Set<Role> userRole = new HashSet<>();
            userRole.add(user);

            // --- Users ---
            List<UserCreationRequest> userRequests = Arrays.asList(
                    UserCreationRequest.builder().username("admin").password("admin123")
                            .userDetail(UserDetailRequest.builder()
                                    .fullName("Quản trị viên")
                                    .email("hoang123@yopmail.com")
                                    .dob(LocalDate.of(2004, 9 , 12))
                                    .gender("MALE").build())
                            .build(),
                    UserCreationRequest.builder().username("huyhoang").password("huyhoang123")
                            .userDetail(UserDetailRequest.builder()
                                    .fullName("Nguyễn Huy Hoàng")
                                    .email("hoang1234@yopmail.com")
                                    .dob(LocalDate.of(2004, 9 , 12))
                                    .gender("FEMALE").build())
                            .build()
                    );
            for (UserCreationRequest req : userRequests) {
                userEntityService.createUser(req);
            }

            UserEntity adminUser = userEntityRepository.findByUsername("admin")
                    .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
            adminUser.setRoles(adminRole);

            userEntityRepository.save(adminUser);
        };
    }
}

