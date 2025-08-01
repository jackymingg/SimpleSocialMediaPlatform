-- 發文儲存程序
DELIMITER $$

CREATE PROCEDURE create_post(
    IN p_user_id BIGINT,
    IN p_title VARCHAR(100),
    IN p_content TEXT
)
BEGIN
INSERT INTO posts(user_id, title, content)
VALUES (p_user_id, p_title, p_content);
END$$

-- 留言儲存程序
CREATE PROCEDURE add_comment(
    IN p_post_id BIGINT,
    IN p_user_id BIGINT,
    IN p_content TEXT
)
BEGIN
INSERT INTO comments(post_id, user_id, content)
VALUES (p_post_id, p_user_id, p_content);
END$$

DELIMITER ;
