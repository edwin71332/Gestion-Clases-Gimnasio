-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gimnasio
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `color` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Fitness','#FF5733'),(2,'Yoga','#6133ff'),(3,'Pilates','#3357FF'),(4,'Crossfit','#FF33A1'),(5,'Cardio','#323232');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clase`
--

DROP TABLE IF EXISTS `clase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clase` (
  `id_clase` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_final` date DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_final` time DEFAULT NULL,
  `duracion` int DEFAULT NULL COMMENT 'Duracion en min de la clase',
  `capacidad` int NOT NULL,
  `estado` enum('Disponible','Llena','Cancelada','Sin_horario') DEFAULT 'Disponible',
  `id_instructor` int NOT NULL,
  `id_categoria` int NOT NULL,
  `id_sala` int NOT NULL,
  PRIMARY KEY (`id_clase`),
  KEY `id_instructor` (`id_instructor`),
  KEY `id_categoria` (`id_categoria`),
  KEY `fk_sala` (`id_sala`),
  CONSTRAINT `clase_ibfk_1` FOREIGN KEY (`id_instructor`) REFERENCES `instructor` (`id_instructor`),
  CONSTRAINT `clase_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`),
  CONSTRAINT `fk_sala` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id_sala`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clase`
--

LOCK TABLES `clase` WRITE;
/*!40000 ALTER TABLE `clase` DISABLE KEYS */;
INSERT INTO `clase` VALUES (27,'Yoga Matutino','Clase de yoga para principiantes','2025-07-15','2025-07-15','06:30:00','07:30:00',60,15,'Disponible',86,1,14),(28,'Pilates Intermedio','Ejercicios de pilates para nivel intermedio','2025-07-16','2025-07-16','08:00:00','09:00:00',60,20,'Disponible',87,2,15),(29,'Zumba Fitness','Clase de zumba para quemar calorías','2025-07-15','2025-07-15','18:00:00','19:00:00',60,25,'Disponible',88,3,16),(30,'Spinning Intensivo','Sesión intensa de spinning','2025-07-17','2025-07-17','07:15:00','08:00:00',45,18,'Disponible',89,4,17),(31,'Entrenamiento Funcional','Entrenamiento para fuerza y resistencia','2025-07-18','2025-07-18','08:30:00','09:00:00',30,12,'Disponible',90,5,18),(32,'Zumba Intermedio','Clase de baile con ritmos latinos','2025-07-19','2025-07-19','09:15:00','10:00:00',45,15,'Disponible',87,1,14),(33,'Fuerza y Tonificación','Entrenamiento con pesas para tonificar','2025-07-20','2025-07-20','17:00:00','18:00:00',60,12,'Disponible',88,2,15),(34,'Crossfit Básico','Introducción al crossfit','2025-07-21','2025-07-21','19:00:00','20:00:00',60,20,'Disponible',89,3,16),(35,'Relajación y Meditación','Técnicas para reducir el estrés','2025-07-22','2025-07-22','06:30:00','07:15:00',45,15,'Disponible',90,4,17);
/*!40000 ALTER TABLE `clase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidad` (
  `id_especialidad` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (15,'Entrenamiento Personal'),(16,'Yoga'),(17,'Pilates'),(18,'Crossfit'),(19,'Zumba'),(20,'Natación'),(21,'Boxeo'),(22,'Cardio'),(23,'Musculación'),(24,'Danza');
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripcion`
--

DROP TABLE IF EXISTS `inscripcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripcion` (
  `id_inscripcion` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_clase` int NOT NULL,
  `estado` enum('En espera','confirmado','cancelado') DEFAULT 'confirmado',
  `fecha_inscripcion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_inscripcion`),
  UNIQUE KEY `id_usuario` (`id_usuario`,`id_clase`),
  KEY `id_clase` (`id_clase`),
  CONSTRAINT `inscripcion_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `inscripcion_ibfk_2` FOREIGN KEY (`id_clase`) REFERENCES `clase` (`id_clase`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripcion`
--

LOCK TABLES `inscripcion` WRITE;
/*!40000 ALTER TABLE `inscripcion` DISABLE KEYS */;
INSERT INTO `inscripcion` VALUES (50,14,27,'confirmado','2025-07-01 00:00:00'),(51,15,28,'confirmado','2025-07-02 00:00:00'),(52,16,29,'confirmado','2025-07-03 00:00:00'),(53,17,30,'confirmado','2025-07-04 00:00:00'),(54,18,31,'confirmado','2025-07-05 00:00:00'),(55,19,32,'confirmado','2025-07-06 00:00:00'),(56,20,33,'confirmado','2025-07-07 00:00:00'),(57,21,34,'confirmado','2025-07-08 00:00:00'),(58,22,35,'confirmado','2025-07-09 00:00:00'),(59,23,27,'confirmado','2025-07-10 00:00:00');
/*!40000 ALTER TABLE `inscripcion` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_check_capacidad_after_insert` AFTER INSERT ON `inscripcion` FOR EACH ROW BEGIN
  DECLARE total INT;
  DECLARE max_capacidad INT;

  -- Contar inscripciones confirmadas
  SELECT COUNT(*) INTO total
  FROM inscripcion
  WHERE id_clase = NEW.id_clase AND estado = 'confirmado';

  -- Obtener la capacidad de la clase
  SELECT capacidad INTO max_capacidad
  FROM clase
  WHERE id_clase = NEW.id_clase;

  -- Si ya está llena, actualizar estado
  IF total >= max_capacidad THEN
    UPDATE clase
    SET estado = 'Llena'
    WHERE id_clase = NEW.id_clase;
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `id_instructor` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `cedula` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_instructor`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `Telefono` (`telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (86,'Juan','Pérez','555-1234','juan.perez@example.com','12345678'),(87,'María','Gómez','555-5678','maria.gomez@example.com','87654321'),(88,'Carlos','Ramírez','555-9012','carlos.ramirez@example.com','11223344'),(89,'Luisa','Fernández','555-3456','luisa.fernandez@example.com','44332211'),(90,'Ana','López','555-7890','ana.lopez@example.com','55667788');
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_especialidad`
--

DROP TABLE IF EXISTS `instructor_especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_especialidad` (
  `id_Instruespecial` int NOT NULL AUTO_INCREMENT,
  `id_instructor` int NOT NULL,
  `id_especialidad` int NOT NULL,
  PRIMARY KEY (`id_Instruespecial`),
  UNIQUE KEY `id_instructor` (`id_instructor`,`id_especialidad`),
  KEY `id_especialidad` (`id_especialidad`),
  CONSTRAINT `instructor_especialidad_ibfk_1` FOREIGN KEY (`id_instructor`) REFERENCES `instructor` (`id_instructor`),
  CONSTRAINT `instructor_especialidad_ibfk_2` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_especialidad`
--

LOCK TABLES `instructor_especialidad` WRITE;
/*!40000 ALTER TABLE `instructor_especialidad` DISABLE KEYS */;
INSERT INTO `instructor_especialidad` VALUES (14,86,15),(15,86,16),(16,87,17),(17,87,18),(18,88,19),(19,88,20),(20,89,21),(21,89,22),(22,90,23),(23,90,24);
/*!40000 ALTER TABLE `instructor_especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `licencia`
--

DROP TABLE IF EXISTS `licencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `licencia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_instructor` int NOT NULL,
  `filename` varchar(255) NOT NULL,
  `filepath` varchar(500) NOT NULL,
  `uploaded_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_instructor` (`id_instructor`),
  CONSTRAINT `licencia_ibfk_1` FOREIGN KEY (`id_instructor`) REFERENCES `instructor` (`id_instructor`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `licencia`
--

LOCK TABLES `licencia` WRITE;
/*!40000 ALTER TABLE `licencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `licencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sala` (
  `id_sala` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  `capacidad` int NOT NULL,
  `estado` enum('Mantenimiento','Disponible','Cerrada') DEFAULT 'Disponible',
  `id_categoria` int NOT NULL,
  PRIMARY KEY (`id_sala`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `sala_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES (14,'Sala A','Sala para entrenamiento funcional',20,'Disponible',1),(15,'Sala B','Sala para yoga y pilates',15,'Disponible',2),(16,'Sala C','Sala para clases de crossfit',25,'Disponible',3),(17,'Sala D','Sala para clases de zumba y cardio',30,'Disponible',4),(18,'Sala E','Sala para natación y rehabilitación',10,'Disponible',5);
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `Apellido` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `cedula` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `cedula_UNIQUE` (`cedula`),
  UNIQUE KEY `telefono_UNIQUE` (`telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (14,'Carlos','Gómez','carlos.gomez@email.com','12345678','555-1234'),(15,'María','López','maria.lopez@email.com','23456789','555-2345'),(16,'Juan','Pérez','juan.perez@email.com','34567890','555-3456'),(17,'Ana','Martínez','ana.martinez@email.com','45678901','555-4567'),(18,'Luis','Rodríguez','luis.rodriguez@email.com','56789012','555-5678'),(19,'Sofía','Díaz','sofia.diaz@email.com','67890123','555-6789'),(20,'Miguel','Fernández','miguel.fernandez@email.com','78901234','555-7890'),(21,'Laura','García','laura.garcia@email.com','89012345','555-8901'),(22,'Diego','Sánchez','diego.sanchez@email.com','90123456','555-9012'),(23,'Lucía','Torres','lucia.torres@email.com','01234567','555-0123'),(24,'Andrés','Ramírez','andres.ramirez@email.com','11223344','555-1122'),(25,'Paula','Vargas','paula.vargas@email.com','22334455','555-2233'),(26,'Fernando','Castillo','fernando.castillo@email.com','33445566','555-3344'),(27,'Natalia','Molina','natalia.molina@email.com','44556677','555-4455'),(28,'Ricardo','Jiménez','ricardo.jimenez@email.com','55667788','555-5566');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gimnasio'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-11  7:08:31
