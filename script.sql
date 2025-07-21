-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.6.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table dolaspharmacy.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `is_primary` tinyint(1) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `zipcode` varchar(255) DEFAULT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9yb2a7d0hyuycecxehfxxykbg` (`user_detail_id`),
  CONSTRAINT `FK9yb2a7d0hyuycecxehfxxykbg` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.address: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_name` varchar(255) NOT NULL,
  `origin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.brand: ~18 rows (approximately)
INSERT INTO `brand` (`brand_name`, `origin`) VALUES
	('Boston Việt Nam', 'Việt Nam'),
	('Công ty Dược Hà Nội', 'Việt Nam'),
	('DHG Pharma', 'Việt Nam'),
	('Domesco', 'Việt Nam'),
	('Dược Hậu Giang', 'Việt Nam'),
	('Imexpharm', 'Việt Nam'),
	('Mediplantex', 'Việt Nam'),
	('Mekophar', 'Việt Nam'),
	('Nam Dược', 'Việt Nam'),
	('OPC', 'Việt Nam'),
	('Pharimexco', 'Việt Nam'),
	('Pymepharco', 'Việt Nam'),
	('Sao Thái Dương', 'Việt Nam'),
	('Savi Pharm', 'Việt Nam'),
	('Stada Việt Nam', 'Việt Nam'),
	('Traphaco', 'Việt Nam'),
	('Vidipha', 'Việt Nam'),
	('Vimedimex', 'Việt Nam');

-- Dumping structure for table dolaspharmacy.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `image_id` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsj6174i465biq0qn31fv2ro00` (`image_id`),
  KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
  CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKllo73u8jwer0wg1qs3blpxxyw` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.category: ~8 rows (approximately)
INSERT INTO `category` (`id`, `name`, `description`, `is_active`, `slug`, `image_id`, `parent_id`) VALUES
	('2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', 'Vitamin Cho U50+', 'Vitamin dành cho người trên 50 tuổi', b'1', 'vitamin-cho-u50', '328c342e-1119-4635-b407-16751be9f0e7', NULL),
	('476d1dc9-1205-4c95-bdc4-1622934e1ca9', 'Vitamin & Khoáng Chất', 'Sản phẩm vitamin và khoáng chất bổ sung', b'1', 'vitamin-khoang-chat', '35825fa9-471a-46ba-9feb-d945c3996e97', NULL),
	('4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', 'Khuyến Mãi Hot', 'Các sản phẩm đang có khuyến mãi hấp dẫn', b'1', 'khuyen-mai-hot', 'c274aa3f-8b34-43ac-9875-9263a3d19f2f', NULL),
	('5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', 'Quà Tặng Sức Khỏe', 'Quà tặng liên quan đến sức khỏe', b'1', 'qua-tang-suc-khoe', 'c6253c00-00ea-46ba-81a7-e347499d9849', NULL),
	('8625a878-14e6-49d7-b953-201a38340c4e', 'Thiết Bị Y Tế', 'Các thiết bị y tế và dụng cụ chăm sóc sức khỏe', b'1', 'thiet-bi-y-te', '6b5d8faf-a4df-4480-8f4d-c2d9bd31ae28', NULL),
	('a34c681d-981e-497e-9e44-c39b286128d4', 'Ung Thư - Bướu', 'Sản phẩm hỗ trợ cho người ung thư, bướu', b'1', 'ung-thu-buou', '9215a094-fd13-4b47-8d1d-00825611eaf7', NULL),
	('c0e26171-143c-4fc2-bf28-ab315ad3a35b', 'Dưỡng Trắng Da', 'Sản phẩm dưỡng trắng và chăm sóc da', b'1', 'duong-trang-da', 'ca8e2437-facf-474e-ad98-bfa6290528a0', NULL),
	('d7614206-dbf7-437f-a42a-9525889cc5f0', 'Vitamin Cho Mẹ', 'Vitamin dành cho phụ nữ mang thai và cho con bú', b'1', 'vitamin-cho-me', '6468772e-a864-4fa6-a7ea-682d4170e4fb', NULL);

-- Dumping structure for table dolaspharmacy.favourites
CREATE TABLE IF NOT EXISTS `favourites` (
  `id` varchar(255) NOT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKs7p1lbsh01lntdm4suualagr6` (`user_detail_id`),
  CONSTRAINT `FKqabm8fyrsb99pq87qyp6f5xv1` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.favourites: ~2 rows (approximately)
INSERT INTO `favourites` (`id`, `user_detail_id`) VALUES
	('1ebab8c9-d3d9-49b9-9e30-a1573ddc93fb', '2f9234e2-77da-4c1c-b2e5-7dad31b48f9f'),
	('902463fd-6cfd-4d08-9512-94111ad35c21', '5c44d1c0-8405-44bd-b306-de934439ab20');

-- Dumping structure for table dolaspharmacy.favourites_products
CREATE TABLE IF NOT EXISTS `favourites_products` (
  `favourites_id` varchar(255) NOT NULL,
  `products_id` varchar(255) NOT NULL,
  UNIQUE KEY `UKnexgp9q3wb0uf1ya6iyowu5e4` (`products_id`),
  KEY `FKcklitj8091y18lpfee19up2uo` (`favourites_id`),
  CONSTRAINT `FKcklitj8091y18lpfee19up2uo` FOREIGN KEY (`favourites_id`) REFERENCES `favourites` (`id`),
  CONSTRAINT `FKj5hmg8y6xere4ql2ur0au7bml` FOREIGN KEY (`products_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.favourites_products: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.image
CREATE TABLE IF NOT EXISTS `image` (
  `id` varchar(255) NOT NULL,
  `alt` varchar(255) DEFAULT NULL,
  `is_primary` bit(1) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.image: ~67 rows (approximately)
INSERT INTO `image` (`id`, `alt`, `is_primary`, `url`) VALUES
	('01251c2c-d6af-4971-88c1-5a617179469a', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_vogxfu.png'),
	('0373f53d-16d4-492c-9999-45d18d329846', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/hebe-tuyp-truoc-908f63e863_xzl4jv.png'),
	('0820bb29-f5a0-47fa-8d14-ba56e1a1c76a', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00503066-son-duong-moi-sebamed-l_iuf3kn.png'),
	('09a77625-8666-408e-ac8f-d84f83abe750', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00032923-vien-uong-cai-thien-tim_m6kd4c.png'),
	('0f09407b-6934-43ce-afbd-d51fea40a6c7', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00501988-sua-cho-benh-nhan-gan-f_pyyoou.png'),
	('0fd36871-b3bc-43b3-83b5-58ba1a0c5cae', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00029929-maxpremum-naga-plus-200_uu8e9w.png'),
	('12f6a5f3-2a84-4a87-ad31-a15a89a38c09', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032923-vien-uong-cai-thien-tim_1_b56yqn.png'),
	('13064f48-246c-473a-b2ee-4a7fb0581f97', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png'),
	('15ffea0d-adf3-4782-a4fa-471019f23222', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341488/dsc-00535-480fad02f8_1_mxnxvw.png'),
	('185d466f-405c-427c-9db7-74560c121ffe', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500119-vien-uong-ho-tro-giam-n_fyciyl.png'),
	('1c591c9d-c4ff-436d-89bf-56dd145ec577', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500955-tra-nhan-sam-ko-ginseng_a9hd5l.png'),
	('1f3f8ef9-f7ad-4d3c-96cd-bd7e18559880', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/img-9003-7e22ddc19e_1_czsgxg.png'),
	('219e6f33-d871-4bec-bacf-689118579962', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502680-vien-uong-lam-dep-da-ch_we06np.png'),
	('22f6c866-d7f8-46ce-8ccc-816d8b63cbc3', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00036-f81526ba97_oojjx4.png'),
	('328c342e-1119-4635-b407-16751be9f0e7', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00031920-top-grow-jpanwell-10-ch-3f81b1a4-df3b-41f3-869d-c64cb90506fa_qble7r.png'),
	('356f8dde-0c1f-4d33-8e40-a361af9ea9e8', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502416-blackmores-executive-b-4_fsoxxw.png'),
	('35825fa9-471a-46ba-9feb-d945c3996e97', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png'),
	('384f7398-62cd-442e-9acc-bffeda7e0e3c', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502680-vien-uong-lam-dep-da-ch_we06np.png'),
	('411a7a0b-824f-496d-a378-2e41d9bc4f45', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png'),
	('433ceb58-ee45-42ec-bead-9465560a4d06', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png'),
	('47c09993-1daf-4ffc-bf32-b46f25c90c20', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00025-00386132d2_ryqm1b.png'),
	('5183a949-7895-431d-9b8e-80dcaf5d9e08', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032923-vien-uong-cai-thien-tim_1_b56yqn.png'),
	('54559071-eb7f-408f-a897-93c5e0c5f9a3', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032923-vien-uong-cai-thien-tim_1_b56yqn.png'),
	('60d5b96a-f078-42fb-b8fe-079a471d90b5', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/hebe-tuyp-truoc-908f63e863_xzl4jv.png'),
	('6468772e-a864-4fa6-a7ea-682d4170e4fb', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00502603-vien-uong-tang-cuong-ch_ytdohn.png'),
	('6596c02b-07e5-48fb-b0ad-c5ac372497f9', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png'),
	('6b5d8faf-a4df-4480-8f4d-c2d9bd31ae28', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341486/00503081-sap-duong-am-vaseline-r_xz0dfq.png'),
	('6ba0298e-1e96-4226-89aa-b6e52f22cf93', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00021988-anica-phytextra-60v-513_fc7gpe.png'),
	('88d8ffaa-95fb-47f7-9d28-7a960e2fbc12', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00032923-vien-uong-cai-thien-tim_m6kd4c.png'),
	('8a4185b7-8b94-40a3-b044-b269899f3bdf', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png'),
	('91093ac1-dd34-4d98-a7ca-a809a12190b0', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_vogxfu.png'),
	('9215a094-fd13-4b47-8d1d-00825611eaf7', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502680-vien-uong-lam-dep-da-ch_we06np.png'),
	('94f158dc-6bc4-4107-b8db-9d121f1f9cd4', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032923-vien-uong-cai-thien-tim_1_b56yqn.png'),
	('9656aac2-dabb-495f-a88e-0d03fab8e6c4', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png'),
	('96e192e1-50d4-412b-b058-e2c81e6f3d0a', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500955-tra-nhan-sam-ko-ginseng_a9hd5l.png'),
	('9ccb9fe8-98c6-41b7-92e0-10c1bb5f05bf', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00025-00386132d2_ryqm1b.png'),
	('a0c76ca0-c80e-4295-b21c-b4beebd0dc28', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/img-9003-7e22ddc19e_1_czsgxg.png'),
	('a0e46c60-fc5e-4d6d-af1f-38b9d50bce52', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341486/00503081-sap-duong-am-vaseline-r_xz0dfq.png'),
	('a25a00f5-2288-4c60-9a06-f8f594aebe2d', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032918-glucosamine-and-chondro_xsbh2t.png'),
	('a6ea143c-5cd0-4736-855e-d9cc52613c39', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png'),
	('a86d36cf-fd67-49d7-ba8d-2a57f73fb334', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00503066-son-duong-moi-sebamed-l_iuf3kn.png'),
	('aa0982f9-3d27-4b80-85f8-3652691a4196', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341486/00503081-sap-duong-am-vaseline-r_xz0dfq.png'),
	('aca6199e-f182-4315-aa40-69202f0b0456', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png'),
	('b95d71ad-8fc7-4d78-9ed1-d9b09e04ce3d', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/00503275-vien-uong-bo-sung-canxi_qjtovs.png'),
	('ba4fc638-613b-429f-9515-8444ef8d2e43', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png'),
	('bda48132-3837-4630-8989-3bda6637d0ca', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png'),
	('bdce2d80-78df-4fcc-b729-ac83214a89ef', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09932-bc701e2141_y7evhl.png'),
	('c1398534-31ed-4bfc-9663-f66d3a1935ad', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00025-00386132d2_ryqm1b.png'),
	('c274aa3f-8b34-43ac-9875-9263a3d19f2f', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500119-vien-uong-ho-tro-giam-n_fyciyl.png'),
	('c2e5855f-32bd-4df9-a41e-b0c522073e0b', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341488/dsc-00535-480fad02f8_1_mxnxvw.png'),
	('c3ea002b-80ae-4b27-ad1a-ca998988b98b', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png'),
	('c42015d1-80fe-493c-9dc4-112bc2b09854', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00029929-maxpremum-naga-plus-200_uu8e9w.png'),
	('c6253c00-00ea-46ba-81a7-e347499d9849', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500119-vien-uong-ho-tro-giam-n_fyciyl.png'),
	('c66dcdd3-f7d0-48b3-9ec5-5a087f34f849', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_1_a0fqlp.png'),
	('c819135f-1f7f-4064-9d0b-0af1f639ff2b', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09932-bc701e2141_y7evhl.png'),
	('ca8e2437-facf-474e-ad98-bfa6290528a0', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00500119-vien-uong-ho-tro-giam-n_fyciyl.png'),
	('cd35dbf2-8164-4998-871b-02b88043d860', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502416-blackmores-executive-b-4_fsoxxw.png'),
	('d357f29c-845d-4727-93e1-a6eb9839e3e7', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/dsc-09866-48ad7ea252_mf8bzn.png'),
	('d7f8be5f-f348-472e-acb1-382d883f5c57', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341489/hebe-tuyp-truoc-908f63e863_xzl4jv.png'),
	('dff8d7b8-77db-4cf1-ab86-ba2d7a5a5115', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00029929-maxpremum-naga-plus-200_uu8e9w.png'),
	('e32e8f31-8aa4-4488-b370-1f417d480bac', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341494/00030869-sasagold-saffron-nhuy-h_btbsu0.png'),
	('ec55c96c-4f4a-438b-88c0-d3ecc3453046', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341487/dsc-00025-00386132d2_ryqm1b.png'),
	('ef8d4981-554c-4f7c-8d0e-471ad15f596d', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341483/00031920-top-grow-jpanwell-10-ch-3f81b1a4-df3b-41f3-869d-c64cb90506fa_qble7r.png'),
	('f1cd31d8-4c76-4fd3-b698-59a2a3b162ca', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00502416-blackmores-executive-b-4_fsoxxw.png'),
	('f9c9505f-6aa3-48ac-9a80-394d5e394496', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341484/00032918-glucosamine-and-chondro_xsbh2t.png'),
	('fb56b742-21cd-4cc1-b30e-e370652bc782', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341495/00031920-top-grow-jpanwell-10-ch_1_a0fqlp.png'),
	('fda1ff93-5d05-453e-80ec-469979b292a8', NULL, b'0', 'https://res.cloudinary.com/dbmtxumro/image/upload/v1746341485/00501988-sua-cho-benh-nhan-gan-f_pyyoou.png');

-- Dumping structure for table dolaspharmacy.invalid_token
CREATE TABLE IF NOT EXISTS `invalid_token` (
  `id` varchar(255) NOT NULL,
  `expire_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.invalid_token: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` varchar(255) NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `order_status` enum('CANCELLED','COMPLETED','PAID','PENDING','SHIPPING','UNPAID') DEFAULT NULL,
  `payment_method` enum('CARD','CASH_ON_DELIVERY','E_BANKING') DEFAULT NULL,
  `receive_date` date DEFAULT NULL,
  `receive_time` varchar(255) DEFAULT NULL,
  `tax` double NOT NULL,
  `total` double NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `promotion_id` varchar(255) DEFAULT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfcgvnpr0f5g1both10s6u1rp4` (`promotion_id`),
  KEY `FKf5464gxwc32ongdvka2rtvw96` (`address_id`),
  KEY `FKhax02af386ywu1ujkep8pe0vg` (`user_detail_id`),
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKhax02af386ywu1ujkep8pe0vg` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`),
  CONSTRAINT `FKkl19lst67x545047o4n1d0jpv` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.orders: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` varchar(255) NOT NULL,
  `final_price` double NOT NULL,
  `quantity` double NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `variant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  KEY `FKbysnyo102axy9hhcx4fnnyx3w` (`variant_id`),
  CONSTRAINT `FKbysnyo102axy9hhcx4fnnyx3w` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.order_item: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.permission
CREATE TABLE IF NOT EXISTS `permission` (
  `permission_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permission_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.permission: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  `ingredients` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `primary_variant_price` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `product_status` enum('ACTIVE','INACTIVE','OUT_OF_BUSSINESS','OUT_OF_STOCK') DEFAULT NULL,
  `requires_prescription` bit(1) NOT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `slug` varchar(255) NOT NULL,
  `usage_instruction` varchar(255) DEFAULT NULL,
  `warning` varchar(255) DEFAULT NULL,
  `brand_id` varchar(255) DEFAULT NULL,
  `catergory_id` varchar(255) DEFAULT NULL,
  `promotion_id` varchar(255) DEFAULT NULL,
  `supplier_id` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_slug` (`slug`),
  UNIQUE KEY `UKcbe0xvgbnbv7ebxy5alyeb5ah` (`promotion_id`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  KEY `FKjmm1t8lt8o33v3kcdgdieqijt` (`catergory_id`),
  KEY `FK2kxvbr72tmtscjvyp9yqb12by` (`supplier_id`),
  KEY `FK4shw90ryeogojlk6m85i4khv8` (`target_id`),
  CONSTRAINT `FK2kxvbr72tmtscjvyp9yqb12by` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `FK4shw90ryeogojlk6m85i4khv8` FOREIGN KEY (`target_id`) REFERENCES `target` (`target_name`),
  CONSTRAINT `FKcli9x921yidy04cx25k6m46fy` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
  CONSTRAINT `FKjmm1t8lt8o33v3kcdgdieqijt` FOREIGN KEY (`catergory_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.product: ~40 rows (approximately)
INSERT INTO `product` (`id`, `created_at`, `description`, `dosage`, `ingredients`, `last_modified_at`, `origin`, `primary_variant_price`, `name`, `product_status`, `requires_prescription`, `sku`, `slug`, `usage_instruction`, `warning`, `brand_id`, `catergory_id`, `promotion_id`, `supplier_id`, `target_id`) VALUES
	('07a6cd05-e128-46d8-9b12-efeabbebc091', '2025-07-21 20:33:19.562891', 'Sản phẩm Thuốc đau dạ dày Yumangel 01 giúp tăng cường sức khỏe. May nghỉ thôi tám hàng nón.', '550mg', 'Thành phần: Cinnamon, Garam Masala', '2025-07-21 20:33:19.564892', 'Việt Nam', 1700000, 'Thuốc đau dạ dày Yumangel 01', 'ACTIVE', b'0', 'SKU-0018', 'thuoc-dau-da-day-yumangel-01', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Phụ nữ mang thai'),
	('07f1ccec-e676-486a-8e6c-39bb3265adf8', '2025-07-21 20:33:19.689588', 'Sản phẩm Nước muối sinh lý 26 giúp tăng cường sức khỏe. Trời may đập gió chín yêu.', '367mg', 'Thành phần: Fennel, Purple Carrot', '2025-07-21 20:33:19.691590', 'Việt Nam', 20000, 'Nước muối sinh lý 26', 'OUT_OF_STOCK', b'0', 'SKU-0035', 'nuoc-muoi-sinh-ly-26', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Vidipha', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Trẻ em'),
	('0a60e864-b133-48ec-b560-631365553698', '2025-07-21 20:33:19.405442', 'Sản phẩm Thuốc dị ứng Telfast 03 giúp tăng cường sức khỏe. Việc mướn thuê giày lỗi ghế.', '843mg', 'Thành phần: Nuts, Yellowtail Kingfish', '2025-07-21 20:33:19.411452', 'Việt Nam', 3620000, 'Thuốc dị ứng Telfast 03', 'ACTIVE', b'0', 'SKU-0001', 'thuoc-di-ung-telfast-03', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Công ty Dược Hà Nội', 'c0e26171-143c-4fc2-bf28-ab315ad3a35b', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Vận động viên'),
	('0bea6914-9303-4ee4-827e-477016c2ade2', '2025-07-21 20:33:19.424050', 'Sản phẩm Thuốc nhỏ mắt V.Rohto 82 giúp tăng cường sức khỏe. Hương yêu giày tím ờ ác phá.', '812mg', 'Thành phần: Juniper Berries, Spelt', '2025-07-21 20:33:19.426051', 'Việt Nam', 3290000, 'Thuốc nhỏ mắt V.Rohto 82', 'ACTIVE', b'0', 'SKU-0002', 'thuoc-nho-mat-vrohto-82', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Pymepharco', '8625a878-14e6-49d7-b953-201a38340c4e', '6fcbd524-35dc-4904-b31f-3213a415fd3c', '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Người cao tuổi'),
	('0e38b3a4-d3df-4bc9-8a86-9ff907cf6c7c', '2025-07-21 20:33:19.491375', 'Sản phẩm Khẩu trang y tế 51 giúp tăng cường sức khỏe. Hết chỉ ba.', '439mg', 'Thành phần: Water, Parrotfish', '2025-07-21 20:33:19.494383', 'Việt Nam', 3480000, 'Khẩu trang y tế 51', 'ACTIVE', b'0', 'SKU-0010', 'khau-trang-y-te-51', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Vimedimex', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Người cao tuổi'),
	('16eb3fe6-e63c-40fd-857a-f879870e23f3', '2025-07-21 20:33:19.659588', 'Sản phẩm Kem đánh răng P/S 43 giúp tăng cường sức khỏe. Đỏ mua nha mượn bảy giày.', '430mg', 'Thành phần: Hiramasa Kingfish, Shiitake Mushrooms', '2025-07-21 20:33:19.661586', 'Việt Nam', 670000, 'Kem đánh răng P/S 43', 'ACTIVE', b'0', 'SKU-0031', 'kem-danh-rang-ps-43', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Imexpharm', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Vận động viên'),
	('213754bd-2f26-4051-845d-d24cfc8dd896', '2025-07-21 20:33:19.712590', 'Sản phẩm Kem chống nắng Anessa 05 giúp tăng cường sức khỏe. Trời áo biển.', '294mg', 'Thành phần: Arugula, Harissa', '2025-07-21 20:33:19.714588', 'Việt Nam', 380000, 'Kem chống nắng Anessa 05', 'ACTIVE', b'0', 'SKU-0038', 'kem-chong-nang-anessa-05', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Vimedimex', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Người cao tuổi'),
	('2ab24936-1aca-4b70-977e-95f3e114f4d9', '2025-07-21 20:33:19.464378', 'Sản phẩm Paracetamol 500mg 50 giúp tăng cường sức khỏe. Con đồng bè đang.', '511mg', 'Thành phần: Oats, Peppers', '2025-07-21 20:33:19.466376', 'Việt Nam', 2810000, 'Paracetamol 500mg 50', 'OUT_OF_STOCK', b'0', 'SKU-0007', 'paracetamol-500mg-50', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Dược Hậu Giang', '476d1dc9-1205-4c95-bdc4-1622934e1ca9', '168c26c6-24b6-4f4c-aa56-ff54983a738e', 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Vận động viên'),
	('2cb7e9a7-9ce2-4dc8-afd3-b0470638e899', '2025-07-21 20:33:19.448792', 'Sản phẩm Kem chống nắng Anessa 41 giúp tăng cường sức khỏe. Được ừ viết mướn bạn đã không mây.', '675mg', 'Thành phần: Kiwi Fruit, Radish', '2025-07-21 20:33:19.449795', 'Việt Nam', 3940000, 'Kem chống nắng Anessa 41', 'ACTIVE', b'0', 'SKU-0005', 'kem-chong-nang-anessa-41', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Vimedimex', 'a34c681d-981e-497e-9e44-c39b286128d4', '42903739-dbba-4ce0-b861-177b051e0dc9', '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Phụ nữ mang thai'),
	('2d14c71e-2f72-4b0d-9455-bc0885806c5c', '2025-07-21 20:33:19.441050', 'Sản phẩm Thuốc bổ máu Ferrovit 87 giúp tăng cường sức khỏe. Gió nghỉ bơi bạn.', '395mg', 'Thành phần: Bacon, Cannellini Beans', '2025-07-21 20:33:19.443051', 'Việt Nam', 750000, 'Thuốc bổ máu Ferrovit 87', 'ACTIVE', b'1', 'SKU-0004', 'thuoc-bo-mau-ferrovit-87', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Boston Việt Nam', 'd7614206-dbf7-437f-a42a-9525889cc5f0', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Vận động viên'),
	('31704fd0-d2a1-479d-b8cb-87d456b2ff1b', '2025-07-21 20:33:19.675591', 'Sản phẩm C sủi 27 giúp tăng cường sức khỏe. Vá ghế hết trăng.', '731mg', 'Thành phần: Arrowroot, Smoked Trout', '2025-07-21 20:33:19.676589', 'Việt Nam', 4930000, 'C sủi 27', 'ACTIVE', b'0', 'SKU-0033', 'c-sui-27', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Traphaco', 'a34c681d-981e-497e-9e44-c39b286128d4', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Trẻ em'),
	('3eb6ac29-afbf-4ded-a73b-1841d52cd18d', '2025-07-21 20:33:19.646592', 'Sản phẩm Dầu gió xanh 79 giúp tăng cường sức khỏe. Máy biết vá mượn xe mượn.', '640mg', 'Thành phần: Scallops, Bean Shoots', '2025-07-21 20:33:19.648593', 'Việt Nam', 4330000, 'Dầu gió xanh 79', 'ACTIVE', b'0', 'SKU-0029', 'dau-gio-xanh-79', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Boston Việt Nam', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Phụ nữ mang thai'),
	('44a78114-d8ea-497a-9a02-be37fa8cc8aa', '2025-07-21 20:33:19.474377', 'Sản phẩm Siro ho Prospan 29 giúp tăng cường sức khỏe. Ruộng làm áo độc.', '350mg', 'Thành phần: Marigold, Carrot', '2025-07-21 20:33:19.475378', 'Việt Nam', 590000, 'Siro ho Prospan 29', 'ACTIVE', b'1', 'SKU-0008', 'siro-ho-prospan-29', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Mekophar', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Trẻ em'),
	('504f7eac-04f8-4f57-9444-b59fe9e3ae9e', '2025-07-21 20:33:19.526890', 'Sản phẩm Kem chống nắng Anessa 48 giúp tăng cường sức khỏe. Bơi khoảng viết ngọt hết quần là gì.', '16mg', 'Thành phần: Calamari, Baking Powder', '2025-07-21 20:33:19.529893', 'Việt Nam', 2730000, 'Kem chống nắng Anessa 48', 'OUT_OF_STOCK', b'0', 'SKU-0014', 'kem-chong-nang-anessa-48', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', '8625a878-14e6-49d7-b953-201a38340c4e', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Vận động viên'),
	('51c5a59f-beb8-4557-bb0b-7579d267e986', '2025-07-21 20:33:19.589907', 'Sản phẩm Thuốc ho Bảo Thanh 87 giúp tăng cường sức khỏe. Em lầu được vá đã lầu.', '402mg', 'Thành phần: Fresh Chillies, Baking Powder', '2025-07-21 20:33:19.591909', 'Việt Nam', 3100000, 'Thuốc ho Bảo Thanh 87', 'OUT_OF_STOCK', b'0', 'SKU-0021', 'thuoc-ho-bao-thanh-87', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Savi Pharm', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Phụ nữ mang thai'),
	('5bb84521-9db5-4922-ac9e-d659f4e5f60b', '2025-07-21 20:33:19.597636', 'Sản phẩm Paracetamol 500mg 57 giúp tăng cường sức khỏe. Đạp chìm ba ác vá phá đỏ viết.', '364mg', 'Thành phần: Olives, Malt Vinegar', '2025-07-21 20:33:19.599631', 'Việt Nam', 280000, 'Paracetamol 500mg 57', 'ACTIVE', b'0', 'SKU-0022', 'paracetamol-500mg-57', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Stada Việt Nam', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Vận động viên'),
	('64d0df65-4bbc-42b3-b0f8-793024ed550d', '2025-07-21 20:33:19.509890', 'Sản phẩm Paracetamol 500mg 92 giúp tăng cường sức khỏe. Ruộng thôi không tím gió ác.', '590mg', 'Thành phần: Pandanus Leaves, Jelly', '2025-07-21 20:33:19.511892', 'Việt Nam', 3470000, 'Paracetamol 500mg 92', 'ACTIVE', b'1', 'SKU-0012', 'paracetamol-500mg-92', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Domesco', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Trẻ em'),
	('68c1be89-69b7-490f-8a61-f4da1459146c', '2025-07-21 20:33:19.604876', 'Sản phẩm Thuốc ho Bảo Thanh 14 giúp tăng cường sức khỏe. Nhà vàng hai.', '14mg', 'Thành phần: Celery, Limes', '2025-07-21 20:33:19.605876', 'Việt Nam', 2920000, 'Thuốc ho Bảo Thanh 14', 'ACTIVE', b'0', 'SKU-0023', 'thuoc-ho-bao-thanh-14', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Mediplantex', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Người cao tuổi'),
	('6ff2e9b4-b989-44a6-b511-288b49544373', '2025-07-21 20:33:19.698587', 'Sản phẩm Siro ho Prospan 05 giúp tăng cường sức khỏe. Ghét vàng mua không.', '844mg', 'Thành phần: Squid, Beef Stock', '2025-07-21 20:33:19.699589', 'Việt Nam', 200000, 'Siro ho Prospan 05', 'ACTIVE', b'1', 'SKU-0036', 'siro-ho-prospan-05', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Vidipha', 'a34c681d-981e-497e-9e44-c39b286128d4', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Người lớn'),
	('7a1c7727-6e4a-4b89-a923-f2e3df910d7d', '2025-07-21 20:33:19.720588', 'Sản phẩm C sủi 51 giúp tăng cường sức khỏe. Quần độc hóa hết ba quần giết mượn.', '796mg', 'Thành phần: Flour, Avocado Oil', '2025-07-21 20:33:19.721587', 'Việt Nam', 450000, 'C sủi 51', 'ACTIVE', b'0', 'SKU-0039', 'c-sui-51', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'OPC', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Vận động viên'),
	('7d4dc4ff-69d2-4cbd-aeef-3f73275cdcbd', '2025-07-21 20:33:19.652590', 'Sản phẩm Kem đánh răng P/S 70 giúp tăng cường sức khỏe. Kim thì yêu chỉ đã dép ghét núi.', '625mg', 'Thành phần: Spinach, Haloumi', '2025-07-21 20:33:19.654590', 'Việt Nam', 2580000, 'Kem đánh răng P/S 70', 'ACTIVE', b'0', 'SKU-0030', 'kem-danh-rang-ps-70', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Mekophar', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Vận động viên'),
	('8be67bce-0042-4f7c-99e0-3773782d44f8', '2025-07-21 20:33:19.517889', 'Sản phẩm Paracetamol 500mg 33 giúp tăng cường sức khỏe. Hết ghế thích đánh.', '544mg', 'Thành phần: Nashi Pear, Extra Virgin Olive Oil', '2025-07-21 20:33:19.519893', 'Việt Nam', 3020000, 'Paracetamol 500mg 33', 'ACTIVE', b'0', 'SKU-0013', 'paracetamol-500mg-33', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', 'a34c681d-981e-497e-9e44-c39b286128d4', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Trẻ em'),
	('8beb0843-584f-4619-8cf7-533fe02b1db8', '2025-07-21 20:33:19.571891', 'Sản phẩm Vitamin C 1000mg 38 giúp tăng cường sức khỏe. Tàu thương bàn trăng bè con hương.', '665mg', 'Thành phần: Vinegar, Brussels Sprouts', '2025-07-21 20:33:19.574892', 'Việt Nam', 220000, 'Vitamin C 1000mg 38', 'ACTIVE', b'0', 'SKU-0019', 'vitamin-c-1000mg-38', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Pharimexco', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Vận động viên'),
	('a52a63a9-d965-4eed-9cb1-26681dfffb22', '2025-07-21 20:33:19.681591', 'Sản phẩm Thuốc bổ máu Ferrovit 97 giúp tăng cường sức khỏe. Khoảng con ruộng xuồng chìm.', '666mg', 'Thành phần: Sunflower Oil, Jicama', '2025-07-21 20:33:19.683591', 'Việt Nam', 4800000, 'Thuốc bổ máu Ferrovit 97', 'ACTIVE', b'0', 'SKU-0034', 'thuoc-bo-mau-ferrovit-97', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Boston Việt Nam', '8625a878-14e6-49d7-b953-201a38340c4e', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Trẻ em'),
	('b1403f41-9f35-46d1-926d-8fc0a3a579e2', '2025-07-21 20:33:19.634592', 'Sản phẩm Viên sủi Berocca 41 giúp tăng cường sức khỏe. Tám ba áo vá khoảng biển.', '376mg', 'Thành phần: Pistachio Nut, Coconut Oil', '2025-07-21 20:33:19.636592', 'Việt Nam', 3850000, 'Viên sủi Berocca 41', 'ACTIVE', b'0', 'SKU-0027', 'vien-sui-berocca-41', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Nam Dược', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Người cao tuổi'),
	('b7c5d5fe-a74a-4db9-919f-74594611e151', '2025-07-21 20:33:19.582890', 'Sản phẩm Thuốc bổ máu Ferrovit 63 giúp tăng cường sức khỏe. Con thương em phá tôi đang chỉ không.', '249mg', 'Thành phần: Hiramasa Kingfish, Cream', '2025-07-21 20:33:19.584890', 'Việt Nam', 3340000, 'Thuốc bổ máu Ferrovit 63', 'ACTIVE', b'1', 'SKU-0020', 'thuoc-bo-mau-ferrovit-63', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Trẻ em'),
	('c095857f-1de4-4028-ae61-20f2c2544abf', '2025-07-21 20:33:19.612878', 'Sản phẩm Thuốc bổ máu Ferrovit 18 giúp tăng cường sức khỏe. Mướn đá khâu.', '344mg', 'Thành phần: Marigold, Lavender Flowers', '2025-07-21 20:33:19.614877', 'Việt Nam', 70000, 'Thuốc bổ máu Ferrovit 18', 'ACTIVE', b'1', 'SKU-0024', 'thuoc-bo-mau-ferrovit-18', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Domesco', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Trẻ em'),
	('c18de1c8-17b4-4b93-966c-e4d288eee70e', '2025-07-21 20:33:19.627881', 'Sản phẩm Thuốc nhỏ mắt V.Rohto 78 giúp tăng cường sức khỏe. Không tủ á.', '557mg', 'Thành phần: Pumpkin Seed, Lettuce', '2025-07-21 20:33:19.629876', 'Việt Nam', 2340000, 'Thuốc nhỏ mắt V.Rohto 78', 'ACTIVE', b'0', 'SKU-0026', 'thuoc-nho-mat-vrohto-78', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Stada Việt Nam', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Trẻ em'),
	('ce7db152-87c6-4314-8fe0-1d9bd125961b', '2025-07-21 20:33:19.668591', 'Sản phẩm Thuốc dị ứng Telfast 29 giúp tăng cường sức khỏe. Lầu vàng năm độc nghỉ.', '357mg', 'Thành phần: Melon, Guava', '2025-07-21 20:33:19.669588', 'Việt Nam', 2190000, 'Thuốc dị ứng Telfast 29', 'ACTIVE', b'1', 'SKU-0032', 'thuoc-di-ung-telfast-29', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Stada Việt Nam', '5225d2e2-e2cf-44c8-ba73-07d751dd7ed2', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Người lớn'),
	('d160c0fe-7ece-47c8-bcd7-0da178d0aef1', '2025-07-21 20:33:19.434049', 'Sản phẩm Băng cá nhân Urgo 58 giúp tăng cường sức khỏe. Vá đập độc thế cái.', '441mg', 'Thành phần: Elderberry, Miso', '2025-07-21 20:33:19.435049', 'Việt Nam', 1940000, 'Băng cá nhân Urgo 58', 'ACTIVE', b'0', 'SKU-0003', 'bang-ca-nhan-urgo-58', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Pymepharco', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', 'a52055a9-0b2f-4c10-94b7-6a861e044a66', 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Phụ nữ mang thai'),
	('d8a3ef02-c369-4bc4-9cb9-5a5f470b0491', '2025-07-21 20:33:19.456377', 'Sản phẩm Vitamin C 1000mg 42 giúp tăng cường sức khỏe. Nón tủ gì trời bơi được.', '856mg', 'Thành phần: Cous Cous, Ricemilk', '2025-07-21 20:33:19.458376', 'Việt Nam', 4440000, 'Vitamin C 1000mg 42', 'ACTIVE', b'0', 'SKU-0006', 'vitamin-c-1000mg-42', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, 'd7d06489-72dd-466c-98e7-30aa83f08907', 'Trẻ em'),
	('dbc87a0b-76b4-422b-a0cd-310f945a16f7', '2025-07-21 20:33:19.640592', 'Sản phẩm Paracetamol 500mg 87 giúp tăng cường sức khỏe. Đạp cái ghét một giết ờ tàu.', '633mg', 'Thành phần: Eggs, Chickory', '2025-07-21 20:33:19.642592', 'Việt Nam', 30000, 'Paracetamol 500mg 87', 'OUT_OF_STOCK', b'1', 'SKU-0028', 'paracetamol-500mg-87', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Boston Việt Nam', 'd7614206-dbf7-437f-a42a-9525889cc5f0', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Vận động viên'),
	('dbd6485d-0efd-4eaf-a050-b27a3d54eb5e', '2025-07-21 20:33:19.545892', 'Sản phẩm Viên sủi Berocca 31 giúp tăng cường sức khỏe. Không may may yêu vẽ.', '254mg', 'Thành phần: Cous Cous, Oregano', '2025-07-21 20:33:19.546891', 'Việt Nam', 2740000, 'Viên sủi Berocca 31', 'ACTIVE', b'1', 'SKU-0016', 'vien-sui-berocca-31', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Mekophar', '476d1dc9-1205-4c95-bdc4-1622934e1ca9', NULL, 'b7a3e7bd-252f-43c1-8f77-96f7400222db', 'Phụ nữ mang thai'),
	('dfcb57a6-dfb4-43a0-beb4-238ec1423d7b', '2025-07-21 20:33:19.619876', 'Sản phẩm Thuốc đau dạ dày Yumangel 43 giúp tăng cường sức khỏe. Á quê đã ác tủ.', '72mg', 'Thành phần: Malt Vinegar, Mountain Bread', '2025-07-21 20:33:19.621880', 'Việt Nam', 3100000, 'Thuốc đau dạ dày Yumangel 43', 'ACTIVE', b'0', 'SKU-0025', 'thuoc-dau-da-day-yumangel-43', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Mekophar', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Vận động viên'),
	('e0df4df6-549b-4d78-b2fd-ff57188f31cc', '2025-07-21 20:33:19.500380', 'Sản phẩm Vitamin C 1000mg 40 giúp tăng cường sức khỏe. Thì việc tô yêu hương.', '352mg', 'Thành phần: Fromage Blanc, Beef', '2025-07-21 20:33:19.502382', 'Việt Nam', 2300000, 'Vitamin C 1000mg 40', 'ACTIVE', b'0', 'SKU-0011', 'vitamin-c-1000mg-40', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Boston Việt Nam', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '73286cfe-da68-4381-bf71-6f58e3734a47', 'Phụ nữ mang thai'),
	('e19a1008-02b0-4f66-905f-95070c56dd44', '2025-07-21 20:33:19.483377', 'Sản phẩm Men tiêu hóa Bio-acimin 87 giúp tăng cường sức khỏe. Bạn thì quê việc.', '263mg', 'Thành phần: Macadamia Oil, Polenta', '2025-07-21 20:33:19.485377', 'Việt Nam', 440000, 'Men tiêu hóa Bio-acimin 87', 'ACTIVE', b'0', 'SKU-0009', 'men-tieu-hoa-bio-acimin-87', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Pharimexco', '2ef03888-b9db-403e-b05c-8cc1ddbbb4ef', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Người cao tuổi'),
	('e2acb78d-69ef-4bc7-9705-c63eaa93c070', '2025-07-21 20:33:19.705588', 'Sản phẩm Thuốc ho Bảo Thanh 41 giúp tăng cường sức khỏe. Leo bốn là thôi con.', '395mg', 'Thành phần: Spring Onions, Bok Choy', '2025-07-21 20:33:19.706589', 'Việt Nam', 1250000, 'Thuốc ho Bảo Thanh 41', 'ACTIVE', b'0', 'SKU-0037', 'thuoc-ho-bao-thanh-41', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Stada Việt Nam', '4e7bca04-5e49-41da-8f8a-2f5bbf1538c5', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Người lớn'),
	('e45f644a-e008-4a05-97b4-7ea4194cf8b4', '2025-07-21 20:33:19.537892', 'Sản phẩm Băng cá nhân Urgo 17 giúp tăng cường sức khỏe. Mua em ruộng gió.', '297mg', 'Thành phần: Hazelnut, Tom Yum', '2025-07-21 20:33:19.539890', 'Việt Nam', 4590000, 'Băng cá nhân Urgo 17', 'ACTIVE', b'0', 'SKU-0015', 'bang-ca-nhan-urgo-17', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Pharimexco', 'a34c681d-981e-497e-9e44-c39b286128d4', NULL, '9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', 'Trẻ em'),
	('eaa77167-821f-4ade-a602-feefcb2b631b', '2025-07-21 20:33:19.727587', 'Sản phẩm Men tiêu hóa Bio-acimin 86 giúp tăng cường sức khỏe. Yêu thuyền may đánh yêu lầu em.', '670mg', 'Thành phần: Macadamia Oil, Taleggio Cheese', '2025-07-21 20:33:19.729588', 'Việt Nam', 4360000, 'Men tiêu hóa Bio-acimin 86', 'ACTIVE', b'1', 'SKU-0040', 'men-tieu-hoa-bio-acimin-86', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'DHG Pharma', 'c0e26171-143c-4fc2-bf28-ab315ad3a35b', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Vận động viên'),
	('fcb3940e-0c6a-414b-8f20-ca679787196c', '2025-07-21 20:33:19.553892', 'Sản phẩm Nước súc miệng Listerine 56 giúp tăng cường sức khỏe. Kim thuyền phá kim bảy bơi.', '362mg', 'Thành phần: Olives, Garam Masala', '2025-07-21 20:33:19.555889', 'Việt Nam', 3010000, 'Nước súc miệng Listerine 56', 'ACTIVE', b'0', 'SKU-0017', 'nuoc-suc-mieng-listerine-56', 'Dùng theo chỉ định của bác sĩ hoặc dược sĩ.', 'Đọc kỹ hướng dẫn sử dụng trước khi dùng.', 'Công ty Dược Hà Nội', '476d1dc9-1205-4c95-bdc4-1622934e1ca9', NULL, '0e5bf0b2-05f1-4b70-afad-84c15d1e7453', 'Người lớn');

-- Dumping structure for table dolaspharmacy.product_images
CREATE TABLE IF NOT EXISTS `product_images` (
  `product_id` varchar(255) NOT NULL,
  `images_id` varchar(255) NOT NULL,
  UNIQUE KEY `UK3701am6d8us1lbn5v3j75yinr` (`images_id`),
  KEY `FKi8jnqq05sk5nkma3pfp3ylqrt` (`product_id`),
  CONSTRAINT `FKd99dtqyhwdbe0ngde824r2ogi` FOREIGN KEY (`images_id`) REFERENCES `image` (`id`),
  CONSTRAINT `FKi8jnqq05sk5nkma3pfp3ylqrt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.product_images: ~59 rows (approximately)
INSERT INTO `product_images` (`product_id`, `images_id`) VALUES
	('07a6cd05-e128-46d8-9b12-efeabbebc091', '219e6f33-d871-4bec-bacf-689118579962'),
	('07f1ccec-e676-486a-8e6c-39bb3265adf8', '13064f48-246c-473a-b2ee-4a7fb0581f97'),
	('0a60e864-b133-48ec-b560-631365553698', '384f7398-62cd-442e-9acc-bffeda7e0e3c'),
	('0bea6914-9303-4ee4-827e-477016c2ade2', 'a25a00f5-2288-4c60-9a06-f8f594aebe2d'),
	('0e38b3a4-d3df-4bc9-8a86-9ff907cf6c7c', 'e32e8f31-8aa4-4488-b370-1f417d480bac'),
	('16eb3fe6-e63c-40fd-857a-f879870e23f3', '9656aac2-dabb-495f-a88e-0d03fab8e6c4'),
	('213754bd-2f26-4051-845d-d24cfc8dd896', '12f6a5f3-2a84-4a87-ad31-a15a89a38c09'),
	('213754bd-2f26-4051-845d-d24cfc8dd896', '6596c02b-07e5-48fb-b0ad-c5ac372497f9'),
	('2ab24936-1aca-4b70-977e-95f3e114f4d9', '54559071-eb7f-408f-a897-93c5e0c5f9a3'),
	('2ab24936-1aca-4b70-977e-95f3e114f4d9', '8a4185b7-8b94-40a3-b044-b269899f3bdf'),
	('2cb7e9a7-9ce2-4dc8-afd3-b0470638e899', 'd7f8be5f-f348-472e-acb1-382d883f5c57'),
	('2d14c71e-2f72-4b0d-9455-bc0885806c5c', '22f6c866-d7f8-46ce-8ccc-816d8b63cbc3'),
	('2d14c71e-2f72-4b0d-9455-bc0885806c5c', '5183a949-7895-431d-9b8e-80dcaf5d9e08'),
	('31704fd0-d2a1-479d-b8cb-87d456b2ff1b', 'cd35dbf2-8164-4998-871b-02b88043d860'),
	('31704fd0-d2a1-479d-b8cb-87d456b2ff1b', 'f9c9505f-6aa3-48ac-9a80-394d5e394496'),
	('3eb6ac29-afbf-4ded-a73b-1841d52cd18d', '09a77625-8666-408e-ac8f-d84f83abe750'),
	('44a78114-d8ea-497a-9a02-be37fa8cc8aa', '15ffea0d-adf3-4782-a4fa-471019f23222'),
	('44a78114-d8ea-497a-9a02-be37fa8cc8aa', 'c3ea002b-80ae-4b27-ad1a-ca998988b98b'),
	('504f7eac-04f8-4f57-9444-b59fe9e3ae9e', '433ceb58-ee45-42ec-bead-9465560a4d06'),
	('504f7eac-04f8-4f57-9444-b59fe9e3ae9e', 'f1cd31d8-4c76-4fd3-b698-59a2a3b162ca'),
	('51c5a59f-beb8-4557-bb0b-7579d267e986', '47c09993-1daf-4ffc-bf32-b46f25c90c20'),
	('51c5a59f-beb8-4557-bb0b-7579d267e986', 'ef8d4981-554c-4f7c-8d0e-471ad15f596d'),
	('5bb84521-9db5-4922-ac9e-d659f4e5f60b', '356f8dde-0c1f-4d33-8e40-a361af9ea9e8'),
	('5bb84521-9db5-4922-ac9e-d659f4e5f60b', 'd357f29c-845d-4727-93e1-a6eb9839e3e7'),
	('64d0df65-4bbc-42b3-b0f8-793024ed550d', '6ba0298e-1e96-4226-89aa-b6e52f22cf93'),
	('68c1be89-69b7-490f-8a61-f4da1459146c', 'a0e46c60-fc5e-4d6d-af1f-38b9d50bce52'),
	('68c1be89-69b7-490f-8a61-f4da1459146c', 'bda48132-3837-4630-8989-3bda6637d0ca'),
	('6ff2e9b4-b989-44a6-b511-288b49544373', '94f158dc-6bc4-4107-b8db-9d121f1f9cd4'),
	('7a1c7727-6e4a-4b89-a923-f2e3df910d7d', '0373f53d-16d4-492c-9999-45d18d329846'),
	('7d4dc4ff-69d2-4cbd-aeef-3f73275cdcbd', 'c42015d1-80fe-493c-9dc4-112bc2b09854'),
	('8be67bce-0042-4f7c-99e0-3773782d44f8', '91093ac1-dd34-4d98-a7ca-a809a12190b0'),
	('8beb0843-584f-4619-8cf7-533fe02b1db8', 'aa0982f9-3d27-4b80-85f8-3652691a4196'),
	('8beb0843-584f-4619-8cf7-533fe02b1db8', 'c819135f-1f7f-4064-9d0b-0af1f639ff2b'),
	('a52a63a9-d965-4eed-9cb1-26681dfffb22', '1c591c9d-c4ff-436d-89bf-56dd145ec577'),
	('b1403f41-9f35-46d1-926d-8fc0a3a579e2', '0820bb29-f5a0-47fa-8d14-ba56e1a1c76a'),
	('b1403f41-9f35-46d1-926d-8fc0a3a579e2', '60d5b96a-f078-42fb-b8fe-079a471d90b5'),
	('b7c5d5fe-a74a-4db9-919f-74594611e151', '0fd36871-b3bc-43b3-83b5-58ba1a0c5cae'),
	('c095857f-1de4-4028-ae61-20f2c2544abf', 'ba4fc638-613b-429f-9515-8444ef8d2e43'),
	('c095857f-1de4-4028-ae61-20f2c2544abf', 'fda1ff93-5d05-453e-80ec-469979b292a8'),
	('c18de1c8-17b4-4b93-966c-e4d288eee70e', '0f09407b-6934-43ce-afbd-d51fea40a6c7'),
	('c18de1c8-17b4-4b93-966c-e4d288eee70e', '185d466f-405c-427c-9db7-74560c121ffe'),
	('ce7db152-87c6-4314-8fe0-1d9bd125961b', '411a7a0b-824f-496d-a378-2e41d9bc4f45'),
	('ce7db152-87c6-4314-8fe0-1d9bd125961b', 'c66dcdd3-f7d0-48b3-9ec5-5a087f34f849'),
	('d160c0fe-7ece-47c8-bcd7-0da178d0aef1', 'b95d71ad-8fc7-4d78-9ed1-d9b09e04ce3d'),
	('d8a3ef02-c369-4bc4-9cb9-5a5f470b0491', '88d8ffaa-95fb-47f7-9d28-7a960e2fbc12'),
	('d8a3ef02-c369-4bc4-9cb9-5a5f470b0491', '9ccb9fe8-98c6-41b7-92e0-10c1bb5f05bf'),
	('dbc87a0b-76b4-422b-a0cd-310f945a16f7', 'c1398534-31ed-4bfc-9663-f66d3a1935ad'),
	('dbd6485d-0efd-4eaf-a050-b27a3d54eb5e', 'aca6199e-f182-4315-aa40-69202f0b0456'),
	('dfcb57a6-dfb4-43a0-beb4-238ec1423d7b', 'a6ea143c-5cd0-4736-855e-d9cc52613c39'),
	('e0df4df6-549b-4d78-b2fd-ff57188f31cc', '01251c2c-d6af-4971-88c1-5a617179469a'),
	('e0df4df6-549b-4d78-b2fd-ff57188f31cc', 'a86d36cf-fd67-49d7-ba8d-2a57f73fb334'),
	('e19a1008-02b0-4f66-905f-95070c56dd44', 'a0c76ca0-c80e-4295-b21c-b4beebd0dc28'),
	('e19a1008-02b0-4f66-905f-95070c56dd44', 'dff8d7b8-77db-4cf1-ab86-ba2d7a5a5115'),
	('e2acb78d-69ef-4bc7-9705-c63eaa93c070', 'ec55c96c-4f4a-438b-88c0-d3ecc3453046'),
	('e45f644a-e008-4a05-97b4-7ea4194cf8b4', '96e192e1-50d4-412b-b058-e2c81e6f3d0a'),
	('e45f644a-e008-4a05-97b4-7ea4194cf8b4', 'bdce2d80-78df-4fcc-b729-ac83214a89ef'),
	('eaa77167-821f-4ade-a602-feefcb2b631b', '1f3f8ef9-f7ad-4d3c-96cd-bd7e18559880'),
	('eaa77167-821f-4ade-a602-feefcb2b631b', 'c2e5855f-32bd-4df9-a41e-b0c522073e0b'),
	('fcb3940e-0c6a-414b-8f20-ca679787196c', 'fb56b742-21cd-4cc1-b30e-e370652bc782');

-- Dumping structure for table dolaspharmacy.promotion
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `discount_amount` double NOT NULL,
  `end_date` date DEFAULT NULL,
  `promotion_name` varchar(255) DEFAULT NULL,
  `promotion_type` enum('FIXED_AMOUNT_ORDER','FIXED_AMOUNT_PRODUCT','PERCENTAGE_ORDER','PERCENTAGE_PRODUCT') DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.promotion: ~8 rows (approximately)
INSERT INTO `promotion` (`id`, `code`, `discount_amount`, `end_date`, `promotion_name`, `promotion_type`, `start_date`) VALUES
	('0424efcf-5b93-49e0-9d50-3e9e395c6656', NULL, 5, NULL, 'Tết sale', NULL, NULL),
	('168c26c6-24b6-4f4c-aa56-ff54983a738e', NULL, 5, NULL, NULL, NULL, NULL),
	('42903739-dbba-4ce0-b861-177b051e0dc9', NULL, 20, NULL, NULL, NULL, NULL),
	('6fcbd524-35dc-4904-b31f-3213a415fd3c', NULL, 10, NULL, NULL, NULL, NULL),
	('a52055a9-0b2f-4c10-94b7-6a861e044a66', NULL, 15, NULL, NULL, NULL, NULL),
	('b439ad22-40b2-49b8-aab4-25691b3c268f', NULL, 10, NULL, 'Khuyến mãi hè', NULL, NULL),
	('d5913355-43f2-4cdb-ac32-91697c1d1e84', NULL, 20, NULL, 'Giảm giá sốc cuối tuần', NULL, NULL),
	('d9b96d7d-2188-4fc9-b2ad-67041ff52b2f', NULL, 15, NULL, 'Mua 1 tặng 1', NULL, NULL);

-- Dumping structure for table dolaspharmacy.role
CREATE TABLE IF NOT EXISTS `role` (
  `rolename` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.role: ~2 rows (approximately)
INSERT INTO `role` (`rolename`, `description`) VALUES
	('ADMIN', NULL),
	('USER', NULL);

-- Dumping structure for table dolaspharmacy.role_permission
CREATE TABLE IF NOT EXISTS `role_permission` (
  `role` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`role`,`permission`),
  KEY `FK8dbhyr3cvowlp4r0cuc578uqn` (`permission`),
  CONSTRAINT `FK8dbhyr3cvowlp4r0cuc578uqn` FOREIGN KEY (`permission`) REFERENCES `permission` (`permission_name`),
  CONSTRAINT `FKcdu069ajq6b1vc22coycmn4eg` FOREIGN KEY (`role`) REFERENCES `role` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.role_permission: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` varchar(255) NOT NULL,
  `active` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.supplier: ~5 rows (approximately)
INSERT INTO `supplier` (`id`, `active`, `code`, `contact_name`, `created_at`, `description`, `email`, `phone`, `name`, `updated_at`, `website`) VALUES
	('0e5bf0b2-05f1-4b70-afad-84c15d1e7453', NULL, NULL, NULL, '2025-07-21 20:33:19.266442', NULL, NULL, NULL, 'Công ty Dược Sài Gòn', '2025-07-21 20:33:19.266442', NULL),
	('73286cfe-da68-4381-bf71-6f58e3734a47', NULL, NULL, NULL, '2025-07-21 20:33:19.264446', NULL, NULL, NULL, 'Công ty Dược Hậu Giang', '2025-07-21 20:33:19.264446', NULL),
	('9f5f7838-ce68-4bd3-b48a-c8ac7aee4c6d', NULL, NULL, NULL, '2025-07-21 20:33:19.268446', NULL, NULL, NULL, 'Công ty Dược Mediplantex', '2025-07-21 20:33:19.268446', NULL),
	('b7a3e7bd-252f-43c1-8f77-96f7400222db', NULL, NULL, NULL, '2025-07-21 20:33:19.267444', NULL, NULL, NULL, 'Công ty Dược OPC', '2025-07-21 20:33:19.267444', NULL),
	('d7d06489-72dd-466c-98e7-30aa83f08907', NULL, NULL, NULL, '2025-07-21 20:33:19.266442', NULL, NULL, NULL, 'Công ty Traphaco', '2025-07-21 20:33:19.266442', NULL);

-- Dumping structure for table dolaspharmacy.target
CREATE TABLE IF NOT EXISTS `target` (
  `target_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`target_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.target: ~5 rows (approximately)
INSERT INTO `target` (`target_name`, `description`) VALUES
	('Người cao tuổi', NULL),
	('Người lớn', NULL),
	('Phụ nữ mang thai', NULL),
	('Trẻ em', NULL),
	('Vận động viên', NULL);

-- Dumping structure for table dolaspharmacy.user_detail
CREATE TABLE IF NOT EXISTS `user_detail` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE','OTHERS') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `verification_status` bit(1) NOT NULL,
  `user_entity_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjyu7dn4p1mh30iadkk13xib42` (`user_entity_id`),
  CONSTRAINT `FKn3gt95602nemr1h9p8us4skf4` FOREIGN KEY (`user_entity_id`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.user_detail: ~2 rows (approximately)
INSERT INTO `user_detail` (`id`, `created_at`, `dob`, `email`, `full_name`, `gender`, `updated_at`, `verification_status`, `user_entity_id`) VALUES
	('2f9234e2-77da-4c1c-b2e5-7dad31b48f9f', '2025-07-21 20:33:20.058753', '2004-09-12', 'hoang123@yopmail.com', 'Quản trị viên', 'MALE', '2025-07-21 20:33:20.058753', b'0', '16bd7ff4-f937-48a2-bcea-7e222fa8d74b'),
	('5c44d1c0-8405-44bd-b306-de934439ab20', '2025-07-21 20:33:20.317980', '2004-09-12', 'hoang1234@yopmail.com', 'Nguyễn Huy Hoàng', 'FEMALE', '2025-07-21 20:33:20.317980', b'0', 'fcb98a93-048a-4105-8b46-735f2e40d3b1');

-- Dumping structure for table dolaspharmacy.user_detail_orders
CREATE TABLE IF NOT EXISTS `user_detail_orders` (
  `user_detail_id` varchar(255) NOT NULL,
  `orders_id` varchar(255) NOT NULL,
  UNIQUE KEY `UK7jsc18udx56d4mjq4ue5hrd21` (`orders_id`),
  KEY `FK3bfe5rq1gch0dhaky4ya7sfj5` (`user_detail_id`),
  CONSTRAINT `FK3bfe5rq1gch0dhaky4ya7sfj5` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`),
  CONSTRAINT `FK4i2j0c7drdctmyowe40kv7jfv` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.user_detail_orders: ~0 rows (approximately)

-- Dumping structure for table dolaspharmacy.user_entity
CREATE TABLE IF NOT EXISTS `user_entity` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2jsk4eakd0rmvybo409wgwxuw` (`username`),
  UNIQUE KEY `UKrl3o8ga682lh8dx5ipgbk35wu` (`user_detail_id`),
  CONSTRAINT `FKp85dtgd92jv5l1xaad3dek22i` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.user_entity: ~2 rows (approximately)
INSERT INTO `user_entity` (`id`, `password`, `username`, `user_detail_id`) VALUES
	('16bd7ff4-f937-48a2-bcea-7e222fa8d74b', '$2a$12$KiipuP5pIkdNQY6YDA9M9.cpFZf6EfL/pNQA55IqY4SwZdFqLV19S', 'admin', '2f9234e2-77da-4c1c-b2e5-7dad31b48f9f'),
	('fcb98a93-048a-4105-8b46-735f2e40d3b1', '$2a$12$O5OH0cFB1F3P63Sk9jUHFOb3dS6b97IyoIB6IdSbCacy/aWIHs2lu', 'huyhoang', '5c44d1c0-8405-44bd-b306-de934439ab20');

-- Dumping structure for table dolaspharmacy.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role`),
  KEY `FK26f1qdx6r8j1ggkgras9nrc1d` (`role`),
  CONSTRAINT `FK26f1qdx6r8j1ggkgras9nrc1d` FOREIGN KEY (`role`) REFERENCES `role` (`rolename`),
  CONSTRAINT `FK79ltvrbu1ni2ad7w7i9vers1k` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.user_role: ~2 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role`) VALUES
	('16bd7ff4-f937-48a2-bcea-7e222fa8d74b', 'ADMIN'),
	('fcb98a93-048a-4105-8b46-735f2e40d3b1', 'USER');

-- Dumping structure for table dolaspharmacy.variant
CREATE TABLE IF NOT EXISTS `variant` (
  `id` varchar(255) NOT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int(11) NOT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjjpllnln6hk6hj98uesgxno00` (`product_id`),
  CONSTRAINT `FKjjpllnln6hk6hj98uesgxno00` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- Dumping data for table dolaspharmacy.variant: ~85 rows (approximately)
INSERT INTO `variant` (`id`, `is_primary`, `name`, `price`, `stock`, `unit`, `product_id`) VALUES
	('05630a5d-8f19-43b3-9c05-c7762aac8dfc', b'1', 'Hộp 10 viên', 1250000, 195, NULL, 'e2acb78d-69ef-4bc7-9705-c63eaa93c070'),
	('05fbc336-98e4-4f48-b6cb-d55ba8de43d9', b'1', 'Hộp 13 viên', 4800000, 172, NULL, 'a52a63a9-d965-4eed-9cb1-26681dfffb22'),
	('0a317ff0-88ee-4778-b6f3-bb8ed9391857', b'0', 'Hộp 42 viên', 4760000, 43, NULL, '16eb3fe6-e63c-40fd-857a-f879870e23f3'),
	('0a3d8ffe-c497-40d8-8006-d1bf65765757', b'1', 'Hộp 35 viên', 280000, 258, NULL, '5bb84521-9db5-4922-ac9e-d659f4e5f60b'),
	('0ccbc351-1439-429b-81ab-51349d959907', b'1', 'Hộp 44 viên', 3290000, 194, NULL, '0bea6914-9303-4ee4-827e-477016c2ade2'),
	('0e8e0994-4daf-4370-afc8-2fc7fadd0f4b', b'1', 'Hộp 11 viên', 440000, 264, NULL, 'e19a1008-02b0-4f66-905f-95070c56dd44'),
	('0ea89ad6-41c5-43d8-a535-7b780f3a24b7', b'1', 'Hộp 25 viên', 4440000, 33, NULL, 'd8a3ef02-c369-4bc4-9cb9-5a5f470b0491'),
	('12fdfe35-1442-4150-beea-94ed6690149d', b'0', 'Hộp 49 viên', 970000, 124, NULL, 'c095857f-1de4-4028-ae61-20f2c2544abf'),
	('187e1023-36f0-41c0-8c73-2a050c01d1d9', b'1', 'Hộp 39 viên', 20000, 238, NULL, '07f1ccec-e676-486a-8e6c-39bb3265adf8'),
	('227fe4f0-2923-4474-a99c-6b10c7fce2a5', b'0', 'Hộp 25 viên', 840000, 171, NULL, '0e38b3a4-d3df-4bc9-8a86-9ff907cf6c7c'),
	('240fcd59-1a44-4217-97b1-9cb6e3ddbcd3', b'0', 'Hộp 49 viên', 2330000, 269, NULL, '07a6cd05-e128-46d8-9b12-efeabbebc091'),
	('2549721f-8ca9-4f9e-8c07-16f59d81d289', b'0', 'Hộp 42 viên', 1190000, 161, NULL, 'a52a63a9-d965-4eed-9cb1-26681dfffb22'),
	('2b3c0be6-93c1-423e-84d3-8f7bf760cad4', b'0', 'Hộp 45 viên', 4140000, 136, NULL, '07f1ccec-e676-486a-8e6c-39bb3265adf8'),
	('2c03d4c6-e39d-4132-95b8-b8952666a799', b'0', 'Hộp 10 viên', 3790000, 184, NULL, '0a60e864-b133-48ec-b560-631365553698'),
	('2dae31bc-4d1f-442e-8d9d-75b8c2f092fd', b'1', 'Hộp 24 viên', 1940000, 178, NULL, 'd160c0fe-7ece-47c8-bcd7-0da178d0aef1'),
	('2e7c1eb7-cf42-4730-b37a-69f5e7eba82f', b'0', 'Hộp 38 viên', 890000, 178, NULL, 'b1403f41-9f35-46d1-926d-8fc0a3a579e2'),
	('2f37a5ea-2511-4083-8de2-1b99e4b4d538', b'0', 'Hộp 20 viên', 2960000, 100, NULL, '0bea6914-9303-4ee4-827e-477016c2ade2'),
	('3295cbb2-929f-440c-9773-1e5012100ab4', b'1', 'Hộp 16 viên', 3850000, 105, NULL, 'b1403f41-9f35-46d1-926d-8fc0a3a579e2'),
	('36be2520-7df5-4484-9498-c2b241540737', b'0', 'Hộp 24 viên', 3880000, 155, NULL, '2d14c71e-2f72-4b0d-9455-bc0885806c5c'),
	('3a028fb6-b214-4317-a726-175c065faa5e', b'1', 'Hộp 40 viên', 1700000, 197, NULL, '07a6cd05-e128-46d8-9b12-efeabbebc091'),
	('3f50b709-edd5-4466-aa20-e71269cecac8', b'1', 'Hộp 40 viên', 30000, 76, NULL, 'dbc87a0b-76b4-422b-a0cd-310f945a16f7'),
	('410a32b3-c2e2-4c1d-9206-be52d7c60132', b'0', 'Hộp 40 viên', 80000, 295, NULL, 'b1403f41-9f35-46d1-926d-8fc0a3a579e2'),
	('41d69231-f944-4fe0-8377-076558df94cd', b'1', 'Hộp 15 viên', 3480000, 113, NULL, '0e38b3a4-d3df-4bc9-8a86-9ff907cf6c7c'),
	('424bf189-713b-4f5a-81e8-a1205de647e1', b'1', 'Hộp 36 viên', 200000, 295, NULL, '6ff2e9b4-b989-44a6-b511-288b49544373'),
	('431dd5d7-3de1-468d-b9eb-be49c28030e6', b'1', 'Hộp 21 viên', 4590000, 25, NULL, 'e45f644a-e008-4a05-97b4-7ea4194cf8b4'),
	('454be5c8-1225-4e53-9e4e-df4112749c37', b'1', 'Hộp 47 viên', 380000, 187, NULL, '213754bd-2f26-4051-845d-d24cfc8dd896'),
	('46353e1a-af06-4699-8e69-c10184aa874e', b'1', 'Hộp 28 viên', 750000, 59, NULL, '2d14c71e-2f72-4b0d-9455-bc0885806c5c'),
	('4a8000f2-739e-43d2-8533-b61ddd9249d2', b'1', 'Hộp 26 viên', 4330000, 130, NULL, '3eb6ac29-afbf-4ded-a73b-1841d52cd18d'),
	('4c5b6e5f-a099-4485-9d3c-5aa15eaa5d05', b'1', 'Hộp 28 viên', 3340000, 227, NULL, 'b7c5d5fe-a74a-4db9-919f-74594611e151'),
	('4f6ce4c1-d423-4010-8983-6021efb5669e', b'1', 'Hộp 40 viên', 4360000, 50, NULL, 'eaa77167-821f-4ade-a602-feefcb2b631b'),
	('530ee550-e3f8-4b99-bc7e-d9ac8ea63272', b'0', 'Hộp 26 viên', 350000, 38, NULL, 'dbc87a0b-76b4-422b-a0cd-310f945a16f7'),
	('56f72f82-e291-4c46-be14-376fa5fbde0d', b'0', 'Hộp 18 viên', 3210000, 274, NULL, '0e38b3a4-d3df-4bc9-8a86-9ff907cf6c7c'),
	('59d6b420-05fa-4d04-84b3-a1b2fb275231', b'0', 'Hộp 10 viên', 1920000, 87, NULL, '8beb0843-584f-4619-8cf7-533fe02b1db8'),
	('5d6f6843-a485-4a7f-b506-7aa0677c2f0b', b'1', 'Hộp 27 viên', 2300000, 170, NULL, 'e0df4df6-549b-4d78-b2fd-ff57188f31cc'),
	('5eacc953-f997-46a1-83ae-74fd87b824c9', b'0', 'Hộp 33 viên', 2040000, 148, NULL, '2ab24936-1aca-4b70-977e-95f3e114f4d9'),
	('6141f489-dbe3-48ce-a7dc-54b213224b60', b'1', 'Hộp 19 viên', 3940000, 141, NULL, '2cb7e9a7-9ce2-4dc8-afd3-b0470638e899'),
	('68579569-982b-41f1-b789-2f45a804dbf3', b'0', 'Hộp 34 viên', 480000, 25, NULL, '7d4dc4ff-69d2-4cbd-aeef-3f73275cdcbd'),
	('68af3b9e-3d67-4f8f-b3b0-a6bcf09aec26', b'0', 'Hộp 31 viên', 1090000, 117, NULL, 'a52a63a9-d965-4eed-9cb1-26681dfffb22'),
	('6ff4786b-fc9a-4264-bb14-c367ca9654bb', b'0', 'Hộp 19 viên', 2010000, 9, NULL, '504f7eac-04f8-4f57-9444-b59fe9e3ae9e'),
	('71373aea-c4bf-4d1c-9cd9-b0f978bca882', b'0', 'Hộp 22 viên', 300000, 125, NULL, 'c18de1c8-17b4-4b93-966c-e4d288eee70e'),
	('750450d1-bf71-46fe-9596-10f2ce09ef6d', b'1', 'Hộp 33 viên', 3620000, 228, NULL, '0a60e864-b133-48ec-b560-631365553698'),
	('7af87b6e-7d2f-45c4-8089-3ee7423f8bb3', b'1', 'Hộp 31 viên', 220000, 64, NULL, '8beb0843-584f-4619-8cf7-533fe02b1db8'),
	('7d5a717e-74b9-40d1-83a9-f573ddf63bdf', b'1', 'Hộp 29 viên', 450000, 201, NULL, '7a1c7727-6e4a-4b89-a923-f2e3df910d7d'),
	('7dc717ff-1a64-411d-b149-035838749440', b'1', 'Hộp 18 viên', 2340000, 135, NULL, 'c18de1c8-17b4-4b93-966c-e4d288eee70e'),
	('7ddfc201-dd59-477a-a7eb-2add073ebc2f', b'1', 'Hộp 27 viên', 2810000, 225, NULL, '2ab24936-1aca-4b70-977e-95f3e114f4d9'),
	('80c15846-6474-4bae-ab63-3e371c23f93d', b'0', 'Hộp 28 viên', 3010000, 130, NULL, '5bb84521-9db5-4922-ac9e-d659f4e5f60b'),
	('81b205c7-1315-420c-8abf-6836bb7b4a91', b'0', 'Hộp 38 viên', 920000, 110, NULL, 'e45f644a-e008-4a05-97b4-7ea4194cf8b4'),
	('83269382-5fa7-4604-b06f-1cf8a7c7d936', b'0', 'Hộp 14 viên', 4720000, 23, NULL, 'e0df4df6-549b-4d78-b2fd-ff57188f31cc'),
	('87ba4f8f-5660-4e4e-9768-cf92716028cd', b'0', 'Hộp 23 viên', 2840000, 207, NULL, '0a60e864-b133-48ec-b560-631365553698'),
	('8889de9a-6617-454c-9705-078e6ec9f0d8', b'1', 'Hộp 29 viên', 3470000, 245, NULL, '64d0df65-4bbc-42b3-b0f8-793024ed550d'),
	('8f563ba3-1ad1-4895-9ae2-30affdd7a347', b'0', 'Hộp 31 viên', 370000, 7, NULL, 'eaa77167-821f-4ade-a602-feefcb2b631b'),
	('8fe80522-0e08-488a-b4ad-e9d820210f57', b'0', 'Hộp 24 viên', 4920000, 35, NULL, 'eaa77167-821f-4ade-a602-feefcb2b631b'),
	('946fb69c-f473-49f8-9c5c-804aa20ce8a9', b'1', 'Hộp 15 viên', 2920000, 222, NULL, '68c1be89-69b7-490f-8a61-f4da1459146c'),
	('94a7693b-2ccf-4415-94d4-ae7aa4a6f9d6', b'1', 'Hộp 22 viên', 3010000, 15, NULL, 'fcb3940e-0c6a-414b-8f20-ca679787196c'),
	('9a8b114b-3841-4818-bf81-f61fa19d5b95', b'0', 'Hộp 16 viên', 520000, 55, NULL, 'e0df4df6-549b-4d78-b2fd-ff57188f31cc'),
	('9cfb0732-7e1d-4ab6-bd74-515173512045', b'0', 'Hộp 19 viên', 220000, 96, NULL, '3eb6ac29-afbf-4ded-a73b-1841d52cd18d'),
	('9e7773ee-fbab-4e61-8b22-f186f93ec136', b'1', 'Hộp 41 viên', 3100000, 138, NULL, 'dfcb57a6-dfb4-43a0-beb4-238ec1423d7b'),
	('9ede976b-753c-4201-8019-03b4ab8fe5dc', b'1', 'Hộp 40 viên', 3100000, 5, NULL, '51c5a59f-beb8-4557-bb0b-7579d267e986'),
	('a1de24d4-e3b5-4722-b2ef-8037d06425c7', b'0', 'Hộp 11 viên', 2740000, 107, NULL, '504f7eac-04f8-4f57-9444-b59fe9e3ae9e'),
	('a26fe308-505f-43bd-b7ea-c3a2082ba6c9', b'1', 'Hộp 45 viên', 2190000, 150, NULL, 'ce7db152-87c6-4314-8fe0-1d9bd125961b'),
	('a29f6f53-d0cf-4423-8257-fd0077870abc', b'0', 'Hộp 13 viên', 2270000, 168, NULL, '51c5a59f-beb8-4557-bb0b-7579d267e986'),
	('a3cbed2b-d633-459c-8182-4e6788b390d6', b'0', 'Hộp 43 viên', 380000, 70, NULL, '51c5a59f-beb8-4557-bb0b-7579d267e986'),
	('a3db779b-560d-4a2e-aae1-15c216d5abfd', b'1', 'Hộp 34 viên', 4930000, 211, NULL, '31704fd0-d2a1-479d-b8cb-87d456b2ff1b'),
	('ae90fac9-5e3d-41d6-bdb2-65a580af0fb9', b'1', 'Hộp 31 viên', 590000, 299, NULL, '44a78114-d8ea-497a-9a02-be37fa8cc8aa'),
	('b4358369-ddb5-4511-86b4-b792d4af3e53', b'0', 'Hộp 22 viên', 2980000, 179, NULL, 'dbc87a0b-76b4-422b-a0cd-310f945a16f7'),
	('b45c7a9b-26bc-4e52-b857-24a3b7a70ab9', b'0', 'Hộp 18 viên', 2260000, 274, NULL, 'b7c5d5fe-a74a-4db9-919f-74594611e151'),
	('b55c913b-9e57-4dad-978e-c1da7e9f57cf', b'0', 'Hộp 47 viên', 1150000, 198, NULL, 'ce7db152-87c6-4314-8fe0-1d9bd125961b'),
	('b57d344f-00c8-4a00-b192-be275c431a94', b'1', 'Hộp 19 viên', 70000, 241, NULL, 'c095857f-1de4-4028-ae61-20f2c2544abf'),
	('b760b016-f7c7-45ab-9495-14f92df5eaff', b'1', 'Hộp 23 viên', 2740000, 244, NULL, 'dbd6485d-0efd-4eaf-a050-b27a3d54eb5e'),
	('b79f3797-da6e-4b88-b102-39056c60af63', b'0', 'Hộp 15 viên', 500000, 200, NULL, '5bb84521-9db5-4922-ac9e-d659f4e5f60b'),
	('bec61ea4-1126-4d04-9560-eb6e4f1e7e58', b'0', 'Hộp 24 viên', 2560000, 110, NULL, '07a6cd05-e128-46d8-9b12-efeabbebc091'),
	('c5f25062-ae3b-46ea-bf18-600e3452bfa0', b'0', 'Hộp 36 viên', 2970000, 97, NULL, 'e19a1008-02b0-4f66-905f-95070c56dd44'),
	('ce1be1c0-57ae-4b26-9fe0-951be4f4548d', b'0', 'Hộp 22 viên', 2940000, 199, NULL, '213754bd-2f26-4051-845d-d24cfc8dd896'),
	('d0a939ef-c65e-48a9-a110-16a4e92db590', b'1', 'Hộp 10 viên', 2580000, 70, NULL, '7d4dc4ff-69d2-4cbd-aeef-3f73275cdcbd'),
	('d2553e0f-f580-4d45-96e6-6273bbb68814', b'0', 'Hộp 29 viên', 1580000, 126, NULL, 'dfcb57a6-dfb4-43a0-beb4-238ec1423d7b'),
	('d3a070f9-20ca-4926-9383-dad3f2d635ff', b'0', 'Hộp 10 viên', 1030000, 225, NULL, 'b7c5d5fe-a74a-4db9-919f-74594611e151'),
	('d744d579-8f86-44ac-b352-7c6491538f1f', b'0', 'Hộp 46 viên', 3270000, 169, NULL, '8beb0843-584f-4619-8cf7-533fe02b1db8'),
	('de2952e3-c581-43fc-8f16-99598778f80a', b'0', 'Hộp 28 viên', 4850000, 165, NULL, '7d4dc4ff-69d2-4cbd-aeef-3f73275cdcbd'),
	('e3c65f86-355c-4f2c-944e-a7108e66a67c', b'1', 'Hộp 18 viên', 670000, 76, NULL, '16eb3fe6-e63c-40fd-857a-f879870e23f3'),
	('e971e38f-a82e-4e0c-9afd-9ab7753e855f', b'0', 'Hộp 38 viên', 4650000, 109, NULL, '2d14c71e-2f72-4b0d-9455-bc0885806c5c'),
	('ee5d971a-ab87-4e4d-96ec-a45b948328fe', b'0', 'Hộp 45 viên', 4000000, 229, NULL, '2ab24936-1aca-4b70-977e-95f3e114f4d9'),
	('f35d8d6c-96d2-47c4-b2d7-cdd3001fd1fc', b'0', 'Hộp 47 viên', 240000, 271, NULL, '07f1ccec-e676-486a-8e6c-39bb3265adf8'),
	('f59da050-60a3-407a-9ce9-19d95d7a886e', b'1', 'Hộp 12 viên', 3020000, 47, NULL, '8be67bce-0042-4f7c-99e0-3773782d44f8'),
	('fd62a92c-8da4-4b0d-87d5-7290fe3410d7', b'1', 'Hộp 28 viên', 2730000, 272, NULL, '504f7eac-04f8-4f57-9444-b59fe9e3ae9e'),
	('fe2f8fa2-390e-41ce-b622-58eb3fdc4753', b'0', 'Hộp 31 viên', 850000, 299, NULL, 'c095857f-1de4-4028-ae61-20f2c2544abf');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
