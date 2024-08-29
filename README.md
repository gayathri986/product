CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` varchar(10) NOT NULL,
  `product_name` varchar(45) NOT NULL,
  `product_category` varchar(45) NOT NULL,
  `product_description` varchar(1000) NOT NULL,
  `product_price` double NOT NULL,
  `product_quantity` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_UNIQUE` (`product_id`)
)ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
