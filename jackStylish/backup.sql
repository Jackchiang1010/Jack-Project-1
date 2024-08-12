-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: stylish
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.22.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `picture` text NOT NULL,
  `story` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'white','#FFFFFF'),(2,'lightgreen','#DDFFBB'),(3,'grey','#CCCCCC'),(4,'lightgreen','#DDFFBB'),(5,'grey','#CCCCCC'),(6,'lightgreen','#DDFFBB'),(7,'grey','#CCCCCC'),(8,'brown','#BB7744'),(9,'lightblue','#DDF0FF'),(10,'grey','#CCCCCC'),(11,'藏青','#334455'),(12,'white','#FFFFFF'),(13,'lightblue','#DDF0FF'),(14,'white','#FFFFFF'),(15,'grey','#CCCCCC'),(16,'藏青','#334455'),(17,'lightblue','#DDF0FF'),(18,'brown','#BB7744'),(19,'brown','#BB7744'),(20,'藏青','#334455'),(21,'white','#FFFFFF'),(22,'pink','#FFDDDD'),(23,'white','#FFFFFF'),(24,'lightblue','#DDF0FF');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot_product`
--

DROP TABLE IF EXISTS `hot_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_product` (
  `product_id` bigint unsigned NOT NULL,
  `hots_title` varchar(255) NOT NULL,
  PRIMARY KEY (`hots_title`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot_product`
--

LOCK TABLES `hot_product` WRITE;
/*!40000 ALTER TABLE `hot_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `hot_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hots`
--

DROP TABLE IF EXISTS `hots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hots` (
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hots`
--

LOCK TABLES `hots` WRITE;
/*!40000 ALTER TABLE `hots` DISABLE KEYS */;
/*!40000 ALTER TABLE `hots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list`
--

DROP TABLE IF EXISTS `list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `variant_id` int NOT NULL,
  `qty` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list`
--

LOCK TABLES `list` WRITE;
/*!40000 ALTER TABLE `list` DISABLE KEYS */;
INSERT INTO `list` VALUES (1,1,17,1),(2,2,17,1),(3,3,17,1),(4,4,17,1),(5,5,17,1),(6,6,17,1),(7,7,17,1),(8,8,17,1),(9,9,17,1);
/*!40000 ALTER TABLE `list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shipping` varchar(255) NOT NULL,
  `payment` varchar(255) NOT NULL,
  `subtotal` int NOT NULL,
  `freight` int NOT NULL,
  `total` int NOT NULL,
  `recipient_id` int NOT NULL,
  `user_id` int NOT NULL,
  `payment_status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'delivery','credit_card',1234,14,1300,1,1,1),(2,'delivery','credit_card',1234,14,1300,2,1,-1),(3,'delivery','credit_card',1234,14,1300,3,1,1),(4,'delivery','credit_card',1234,14,1300,4,1,-1),(5,'delivery','credit_card',1234,14,1300,5,1,-1),(6,'delivery','credit_card',1234,14,1300,6,1,1),(7,'delivery','credit_card',1234,14,1300,7,1,1),(8,'delivery','credit_card',1234,14,1300,8,1,-1),(9,'delivery','credit_card',1234,14,1300,9,1,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` int NOT NULL,
  `texture` varchar(255) NOT NULL,
  `wash` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `note` text NOT NULL,
  `story` text NOT NULL,
  `main_image` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'前開衩扭結洋裝','women','厚薄：薄,彈性：無',799,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/9efe3929-fb39-4bb4-b35d-b32c70bfc20b_前開衩扭結洋裝.jpeg'),(2,'透肌澎澎防曬襯衫','women','厚薄：薄,彈性：無',599,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/c78bff62-f290-4fbe-9106-44fb80cd08f8_透肌澎澎防曬襯衫.jpg'),(3,'小扇紋細織上衣','women','厚薄：薄,彈性：無',599,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/56cc2a45-2831-47c1-88a3-5065cea3ad18_小扇紋細織上衣.jpg'),(4,'活力花紋長筒牛仔褲','women','厚薄：薄,彈性：無',1299,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/3fe5e837-8c28-4a16-9534-5669a282c08e_活力花紋長筒牛仔褲.jpg'),(5,'純色輕薄百搭襯衫','men','厚薄：薄,彈性：無',799,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/4bf939f9-e259-4fcf-bf88-30ef60683edc_純色輕薄百搭襯衫.jpg'),(6,'時尚輕鬆休閒西裝','men','厚薄：薄,彈性：無',2399,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/b5338d91-7a3f-4c22-ab90-4c28d76b002e_時尚輕鬆休閒西裝.jpg'),(7,'經典商務西裝','men','厚薄：薄,彈性：無',3999,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/e8d4c13d-6ceb-480f-ab9a-5caed65f32ab_經典商務西裝.jpg'),(8,'夏日海灘戶外遮陽帽','accessories','厚薄：薄,彈性：無',1499,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/e1854d19-7648-40e2-ab9d-e3dc72e9faa1_夏日海灘戶外遮陽帽.jpg'),(9,'經典牛仔帽','accessories','厚薄：薄,彈性：無',799,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/3fd6a7e5-4db6-4626-bed2-c0ac2f500561_經典牛仔帽.jpg'),(10,'卡哇伊多功能隨身包','accessories','厚薄：薄,彈性：無',1299,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/21b74ebf-2664-4634-9b50-925093238e46_卡哇伊多功能隨身包3.jpg'),(11,'柔軟氣質羊毛圍巾','accessories','厚薄：薄,彈性：無',1799,'棉 100%','手洗','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','http://54.248.138.109/uploads/5826ed44-ac87-4a08-b1e2-288ad84c9156_柔軟氣質羊毛圍巾.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `images` varchar(255) NOT NULL,
  `product_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'http://54.248.138.109/uploads/1d7b76c7-9726-4738-a36d-1bcfa0c37d27_前開衩扭結洋裝2.jpeg',1),(2,'http://54.248.138.109/uploads/fa2167f0-ced2-4d22-bd56-503299413d4d_前開衩扭結洋裝3.jpeg',1),(3,'http://54.248.138.109/uploads/3436dab5-cb83-4233-a90c-c58cf1de7b5f_透肌澎澎防曬襯衫2.jpg',2),(4,'http://54.248.138.109/uploads/0703b32e-df57-426d-be69-c536281bd9b1_透肌澎澎防曬襯衫3.jpg',2),(5,'http://54.248.138.109/uploads/2354e295-c029-43fe-a539-8a40f52d3804_小扇紋細織上衣2.jpg',3),(6,'http://54.248.138.109/uploads/e353e495-f5fd-4dfd-9dc6-36408a02f46f_小扇紋細織上衣3.jpg',3),(7,'http://54.248.138.109/uploads/1476ab7d-0da0-4258-bfa4-90f9d9d49d23_活力花紋長筒牛仔褲2.jpg',4),(8,'http://54.248.138.109/uploads/60c5bd2c-b3cb-4db6-8217-2acf606f6fcc_活力花紋長筒牛仔褲3.jpg',4),(9,'http://54.248.138.109/uploads/4ead21ab-da78-4398-95c1-b8c844da55b4_純色輕薄百搭襯衫2.jpg',5),(10,'http://54.248.138.109/uploads/6f561120-d3d4-4e8c-b72e-a6b6c3350917_純色輕薄百搭襯衫3.jpg',5),(11,'http://54.248.138.109/uploads/e3b68238-90a8-4acc-8bb9-d76a85d62a13_時尚輕鬆休閒西裝2.jpg',6),(12,'http://54.248.138.109/uploads/f5672622-8461-4384-bd4a-c4c603e59179_時尚輕鬆休閒西裝3.jpg',6),(13,'http://54.248.138.109/uploads/996ce6d1-f4c6-47b3-8f2f-1126808533fd_經典商務西裝2.jpg',7),(14,'http://54.248.138.109/uploads/decf6c33-6579-4c8b-8d0c-c33ef29bcb8a_經典商務西裝3.jpg',7),(15,'http://54.248.138.109/uploads/f2a58918-8c78-4fd0-a933-30e147c4a57d_夏日海灘戶外遮陽帽2.jpg',8),(16,'http://54.248.138.109/uploads/1fbb9528-a78f-40c0-96e0-9ec03d956614_夏日海灘戶外遮陽帽3.jpg',8),(17,'http://54.248.138.109/uploads/76d1f150-bfa9-455e-9e0d-df6ad9efa1cd_經典牛仔帽2.jpg',9),(18,'http://54.248.138.109/uploads/35e75abf-ec3d-401c-8a43-0e0a369d512c_經典牛仔帽3.jpg',9),(19,'http://54.248.138.109/uploads/1945651a-3845-41de-8064-65b6fcce3880_卡哇伊多功能隨身包.jpg',10),(20,'http://54.248.138.109/uploads/95da6d48-6a0f-4ada-a444-02fc018df0fc_卡哇伊多功能隨身包2.jpg',10),(21,'http://54.248.138.109/uploads/4e533149-1383-4c87-aa1e-be3fc584ab5f_柔軟氣質羊毛圍巾2.jpg',11),(22,'http://54.248.138.109/uploads/3f50b89e-b9f2-4cc4-9795-179469f3fa6f_柔軟氣質羊毛圍巾3.jpg',11);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipient`
--

DROP TABLE IF EXISTS `recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipient`
--

LOCK TABLES `recipient` WRITE;
/*!40000 ALTER TABLE `recipient` DISABLE KEYS */;
INSERT INTO `recipient` VALUES (1,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(2,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(3,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(4,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(5,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(6,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(7,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(8,'Luke','0987654321','luke@gmail.com','市政府站','morning'),(9,'Luke','0987654321','luke@gmail.com','市政府站','morning');
/*!40000 ALTER TABLE `recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'S'),(2,'M'),(3,'L'),(4,'S'),(5,'M'),(6,'S'),(7,'M'),(8,'L'),(9,'S'),(10,'M'),(11,'L'),(12,'M'),(13,'L'),(14,'M'),(15,'L'),(16,'L'),(17,'M'),(18,'L'),(19,'M'),(20,'L'),(21,'S'),(22,'F'),(23,'S'),(24,'F');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'native','test','test@test.com','$2a$10$wAbahIQPqyXFCkwHyKgBPe5TodViO1wOmcpCQqMQ8UW3ukT29CEFu',''),(2,'native','','test2@test.com','$2a$10$Hd3Qq5Cr/VC6zayoGuedP.7O6hymB1pcX50/G8b0EAP2fel1qyWB.',''),(3,'native','kash','kash2@test.com','$2a$10$jXtkRktI7tBCHaq3BAd0TuLOX53J1L7gXViMbTKF8joU2HHgL4FWO','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `color_code` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL,
  `stock` int NOT NULL,
  `product_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (1,'#FFFFFF','S',100,1),(2,'#FFFFFF','M',100,1),(3,'#FFFFFF','L',100,1),(4,'#DDFFBB','S',100,1),(5,'#DDFFBB','M',100,1),(6,'#DDFFBB','L',100,1),(7,'#CCCCCC','S',100,1),(8,'#CCCCCC','M',100,1),(9,'#CCCCCC','L',100,1),(10,'#DDFFBB','S',100,2),(11,'#DDFFBB','M',100,2),(12,'#CCCCCC','S',100,2),(13,'#CCCCCC','M',100,2),(14,'#DDFFBB','S',100,3),(15,'#DDFFBB','M',100,3),(16,'#DDFFBB','L',100,3),(17,'#CCCCCC','S',100,3),(18,'#CCCCCC','M',100,3),(19,'#CCCCCC','L',100,3),(20,'#BB7744','S',100,3),(21,'#BB7744','M',100,3),(22,'#BB7744','L',100,3),(23,'#DDF0FF','S',100,4),(24,'#DDF0FF','M',100,4),(25,'#DDF0FF','L',100,4),(26,'#CCCCCC','S',100,4),(27,'#CCCCCC','M',100,4),(28,'#CCCCCC','L',100,4),(29,'#334455','S',100,4),(30,'#334455','M',100,4),(31,'#334455','L',100,4),(32,'#FFFFFF','M',100,5),(33,'#FFFFFF','L',100,5),(34,'#DDF0FF','M',100,5),(35,'#DDF0FF','L',100,5),(36,'#FFFFFF','M',100,6),(37,'#FFFFFF','L',100,6),(38,'#CCCCCC','M',100,6),(39,'#CCCCCC','L',100,6),(40,'#334455','L',100,7),(41,'#DDF0FF','M',100,8),(42,'#DDF0FF','L',100,8),(43,'#BB7744','M',100,8),(44,'#BB7744','L',100,8),(45,'#BB7744','M',100,9),(46,'#BB7744','L',100,9),(47,'#334455','M',100,9),(48,'#334455','L',100,9),(49,'#FFFFFF','S',100,10),(50,'#FFFFFF','F',100,10),(51,'#FFDDDD','S',100,10),(52,'#FFDDDD','F',100,10),(53,'#FFFFFF','S',100,11),(54,'#FFFFFF','F',100,11),(55,'#DDF0FF','S',100,11),(56,'#DDF0FF','F',100,11);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-04  1:42:29
