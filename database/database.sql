-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: is1-audio-streaming
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audio_kategorija`
--

DROP TABLE IF EXISTS `audio_kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_kategorija` (
  `audio_kategorija_id` int NOT NULL AUTO_INCREMENT,
  `audio_id` int NOT NULL,
  `kategorija_id` int NOT NULL,
  PRIMARY KEY (`audio_kategorija_id`),
  UNIQUE KEY `audio_id` (`audio_id`,`kategorija_id`),
  KEY `kategorija_id` (`kategorija_id`),
  CONSTRAINT `audio_kategorija_ibfk_1` FOREIGN KEY (`audio_id`) REFERENCES `audio_snimak` (`audio_id`) ON DELETE CASCADE,
  CONSTRAINT `audio_kategorija_ibfk_2` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija` (`kategorija_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audio_kategorija`
--

LOCK TABLES `audio_kategorija` WRITE;
/*!40000 ALTER TABLE `audio_kategorija` DISABLE KEYS */;
INSERT INTO `audio_kategorija` VALUES (4,4,4);
/*!40000 ALTER TABLE `audio_kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audio_snimak`
--

DROP TABLE IF EXISTS `audio_snimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_snimak` (
  `audio_id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(200) NOT NULL,
  `trajanje` int NOT NULL,
  `vlasnik_id` int NOT NULL,
  `datum_vreme_postavljanja` datetime NOT NULL,
  PRIMARY KEY (`audio_id`),
  KEY `idx_audio_vlasnik` (`vlasnik_id`),
  CONSTRAINT `audio_snimak_ibfk_1` FOREIGN KEY (`vlasnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE,
  CONSTRAINT `audio_snimak_chk_1` CHECK ((`trajanje` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audio_snimak`
--

LOCK TABLES `audio_snimak` WRITE;
/*!40000 ALTER TABLE `audio_snimak` DISABLE KEYS */;
INSERT INTO `audio_snimak` VALUES (4,'Uvod u programiranje',2700,4,'2024-02-04 14:45:00'),(5,'Moja nova pesma',240,1,'2025-07-06 14:56:58');
/*!40000 ALTER TABLE `audio_snimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `istorija_slusanja`
--

DROP TABLE IF EXISTS `istorija_slusanja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `istorija_slusanja` (
  `istorija_slusanja_id` int NOT NULL AUTO_INCREMENT,
  `korisnik_id` int NOT NULL,
  `audio_id` int NOT NULL,
  `datum_vreme_pocetka` datetime NOT NULL,
  `pocetni_sekund` int NOT NULL,
  `broj_odslusanih_sekundi` int NOT NULL,
  PRIMARY KEY (`istorija_slusanja_id`),
  KEY `audio_id` (`audio_id`),
  KEY `idx_istorija_slusanja_korisnik` (`korisnik_id`),
  CONSTRAINT `istorija_slusanja_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE,
  CONSTRAINT `istorija_slusanja_ibfk_2` FOREIGN KEY (`audio_id`) REFERENCES `audio_snimak` (`audio_id`) ON DELETE CASCADE,
  CONSTRAINT `istorija_slusanja_chk_1` CHECK ((`pocetni_sekund` >= 0)),
  CONSTRAINT `istorija_slusanja_chk_2` CHECK ((`broj_odslusanih_sekundi` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `istorija_slusanja`
--

LOCK TABLES `istorija_slusanja` WRITE;
/*!40000 ALTER TABLE `istorija_slusanja` DISABLE KEYS */;
INSERT INTO `istorija_slusanja` VALUES (4,4,4,'2024-02-06 14:00:00',0,2700);
/*!40000 ALTER TABLE `istorija_slusanja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `kategorija_id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  PRIMARY KEY (`kategorija_id`),
  UNIQUE KEY `naziv` (`naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (3,'Audio knjiga'),(5,'Elektronska muzika'),(1,'Muzika'),(2,'Podkast'),(4,'Predavanje'),(6,'Rep');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `korisnik_id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `godiste` int NOT NULL,
  `pol` enum('MUSKI','ZENSKI','DRUGO') NOT NULL,
  `mesto_id` int NOT NULL,
  PRIMARY KEY (`korisnik_id`),
  UNIQUE KEY `email` (`email`),
  KEY `mesto_id` (`mesto_id`),
  KEY `idx_korisnik_email` (`email`),
  CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`mesto_id`) REFERENCES `mesto` (`mesto_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Marko Markovic','marko.novi@email.com',1995,'MUSKI',1),(2,'Ana Anic','novi.email@test.testa',1998,'ZENSKI',2),(3,'Jovan Jovanovic','jovan@email.com',1990,'MUSKI',3),(4,'Mila Milic','mila@email.com',1993,'ZENSKI',1),(5,'Jovan','Test',2002,'MUSKI',2),(23,'Petar Petrovic','petar@email.com',1992,'MUSKI',1);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `mesto_id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  PRIMARY KEY (`mesto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Beograd'),(2,'Toronto'),(3,'London'),(4,'Kragujevac'),(5,'novoMesto'),(73,'Test for mesto'),(74,'Test for mesto');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `ocena_id` int NOT NULL AUTO_INCREMENT,
  `korisnik_id` int NOT NULL,
  `audio_id` int NOT NULL,
  `vrednost` int NOT NULL,
  `datum_vreme_ocene` datetime NOT NULL,
  PRIMARY KEY (`ocena_id`),
  UNIQUE KEY `korisnik_id` (`korisnik_id`,`audio_id`),
  KEY `idx_ocena_audio` (`audio_id`),
  CONSTRAINT `ocena_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE,
  CONSTRAINT `ocena_ibfk_2` FOREIGN KEY (`audio_id`) REFERENCES `audio_snimak` (`audio_id`) ON DELETE CASCADE,
  CONSTRAINT `ocena_chk_1` CHECK ((`vrednost` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (4,4,4,4,'2024-02-06 15:00:00');
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omiljeni_audio`
--

DROP TABLE IF EXISTS `omiljeni_audio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omiljeni_audio` (
  `omiljeni_audio_id` int NOT NULL AUTO_INCREMENT,
  `korisnik_id` int NOT NULL,
  `audio_id` int NOT NULL,
  PRIMARY KEY (`omiljeni_audio_id`),
  UNIQUE KEY `korisnik_id` (`korisnik_id`,`audio_id`),
  KEY `audio_id` (`audio_id`),
  CONSTRAINT `omiljeni_audio_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE,
  CONSTRAINT `omiljeni_audio_ibfk_2` FOREIGN KEY (`audio_id`) REFERENCES `audio_snimak` (`audio_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omiljeni_audio`
--

LOCK TABLES `omiljeni_audio` WRITE;
/*!40000 ALTER TABLE `omiljeni_audio` DISABLE KEYS */;
INSERT INTO `omiljeni_audio` VALUES (4,4,4);
/*!40000 ALTER TABLE `omiljeni_audio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `paket_id` int NOT NULL AUTO_INCREMENT,
  `trenutna_cena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`paket_id`),
  CONSTRAINT `paket_chk_1` CHECK ((`trenutna_cena` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,1099.99),(2,1999.99),(3,2999.99),(4,1299.99);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `pretplata_id` int NOT NULL AUTO_INCREMENT,
  `korisnik_id` int NOT NULL,
  `paket_id` int NOT NULL,
  `datum_vreme_pocetka` datetime NOT NULL,
  `placena_cena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`pretplata_id`),
  UNIQUE KEY `korisnik_id` (`korisnik_id`),
  KEY `paket_id` (`paket_id`),
  KEY `idx_pretplata_korisnik_datum` (`korisnik_id`,`datum_vreme_pocetka`),
  CONSTRAINT `pretplata_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`) ON DELETE CASCADE,
  CONSTRAINT `pretplata_ibfk_2` FOREIGN KEY (`paket_id`) REFERENCES `paket` (`paket_id`) ON DELETE CASCADE,
  CONSTRAINT `pretplata_chk_1` CHECK ((`placena_cena` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,1,1,'2024-01-01 00:00:00',999.99),(2,2,2,'2024-01-15 00:00:00',1999.99),(3,3,3,'2024-02-01 00:00:00',2999.99),(4,4,1,'2025-07-06 17:02:28',999.99);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-10 14:17:39
