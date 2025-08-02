-- 建立資料庫
CREATE DATABASE IF NOT EXISTS social_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用該資料庫
USE social_platform;

-- 刪除舊資料表
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_name VARCHAR(50) NOT NULL,
                       phone VARCHAR(20) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       cover_image VARCHAR(255),
                       biography TEXT
);

CREATE TABLE posts (
                       post_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       content TEXT NOT NULL,
                       image VARCHAR(255),
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE comments (
                          comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          user_id BIGINT NOT NULL,
                          post_id BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES users(user_id),
                          FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

-- 建立註冊SP
DELIMITER $$

CREATE PROCEDURE sp_register_user (
    IN p_phone VARCHAR(20),
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(255),
    IN p_user_name VARCHAR(50)
)
BEGIN
    INSERT INTO users (phone, email, password, user_name)
    VALUES (p_phone, p_email, p_password, p_user_name);
END $$

DELIMITER ;

-- 建立發文SP
DELIMITER $$

CREATE PROCEDURE sp_create_post (
    IN p_user_id BIGINT,
    IN p_content TEXT,
    IN p_image VARCHAR(255)
)
BEGIN
    INSERT INTO posts (user_id, content, image, created_at)
    VALUES (p_user_id, p_content, p_image, NOW());
END $$

DELIMITER ;

-- 建立留言SP
DELIMITER $$

CREATE PROCEDURE sp_add_comment (
    IN p_user_id BIGINT,
    IN p_post_id BIGINT,
    IN p_content TEXT
)
BEGIN
    INSERT INTO comments (user_id, post_id, content, created_at)
    VALUES (p_user_id, p_post_id, p_content, NOW());
END $$

DELIMITER ;



