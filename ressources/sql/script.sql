CREATE DATABASE IF NOT EXISTS `mdd`;
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE `mdd`;

CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL CHECK (LENGTH(`name`) >= 3),
  `admin` BOOLEAN NOT NULL DEFAULT false,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL CHECK (LENGTH(`password`) >= 8),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `THEMES`(
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL CHECK (LENGTH(`title`) >= 2),
  `description` TEXT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `USER_THEMES`(
  `user_id` INT,
  `theme_id` INT,
  PRIMARY KEY (user_id, theme_id),
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`),
  FOREIGN KEY (`theme_id`) REFERENCES `THEMES` (`id`)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `ARTICLES`(
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL CHECK (LENGTH(`title`) >= 2),
  `user_id` INT,
  `content` TEXT NOT NULL,
  `theme_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`theme_id`) REFERENCES `THEMES` (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `COMMENTS`(
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `content` TEXT NOT NULL CHECK (LENGTH(`content`) >= 2),
  `user_id` INT,
  `article_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`),
  FOREIGN KEY (`article_id`) REFERENCES `ARTICLES` (`id`)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO USERS (name, admin, email, password)
VALUES ('User', true, 'test@gmail.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq');

INSERT INTO THEMES (title, description)
VALUES ('Thème 1', 'Description 1'),
('Thème 2', 'Description 2'),
('Thème supplémentaire', 'Description supplémentaire'),
('Encore un autre thème', 'Et sa description...'),
('Thème 5', 'Un thème très intéressant'),
('Un petit dernier...', '...pour la route');

