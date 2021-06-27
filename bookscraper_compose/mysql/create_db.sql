DROP DATABASE IF EXISTS `book_storage`;

CREATE DATABASE `book_storage`;

use `book_storage`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` varchar(40) DEFAULT NULL,
  `cover` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `subtitle` varchar(200) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `about_the_author` varchar(1000) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `about_the_book` varchar(1000) DEFAULT NULL,
  `readers` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2rqqpgnve6gu3ar7prin5qm0i` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `chapter`;

CREATE TABLE `chapter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `native_chapter_id` varchar(40) DEFAULT NULL,
  `native_book_id` varchar(40) DEFAULT '5',
  `order_no` int DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `text` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `audioUrl` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4sx1e6m3xlo807ukuni2fw1ej` (`native_book_id`),
  CONSTRAINT `FK4sx1e6m3xlo807ukuni2fw1ej` FOREIGN KEY (`native_book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;