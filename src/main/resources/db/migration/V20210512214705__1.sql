CREATE TABLE IF NOT EXISTS `user`(
    `id`                INT NOT NULL AUTO_INCREMENT COMMENT '自增主鍵',
    `username`          VARCHAR(64) NOT NULL COMMENT '使用者名稱',
    `password`          VARCHAR(128) NOT NULL COMMENT '密碼',
    `email`             VARCHAR(128) NOT NULL COMMENT 'Email',
    `phone`             VARCHAR(16) NOT NULL COMMENT '電話號碼',
    `last_login_time`   TIMESTAMP COMMENT '上次登入時間',
    `status`            INT(1) DEFAULT 1 COMMENT '帳號狀態 0 = 凍結 / 1 正常',
    PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT = '使用者';