
DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` bigint NOT NULL AUTO_INCREMENT,
  `patient_lastname` varchar(50) NOT NULL,
  `patient_firstname` varchar(50) NOT NULL,
  `patient_birthdate` date NOT NULL,
  `patient_gender` varchar(1) NOT NULL,
  `patient_address` varchar(255) DEFAULT NULL,
  `patient_phoneNumber` varchar(255) DEFAULT NULL,
  `patient_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;