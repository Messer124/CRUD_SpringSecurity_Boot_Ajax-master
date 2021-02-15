CREATE TABLE IF NOT EXISTS `users`
(
    `id`       bigint(11)  NOT NULL AUTO_INCREMENT,
    `username` varchar(45) NOT NULL,
    `email`    varchar(45) NOT NULL,
    `password` varchar(64) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`)
);

CREATE TABLE IF NOT EXISTS `roles`
(
    `id`   bigint(11)     NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `users_roles`
(
    `user_id` bigint(11) NOT NULL,
    `role_id` bigint(11) NOT NULL,
    KEY `user_fk_idx` (`user_id`),
    KEY `role_fk_idx` (`role_id`),
    CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, email, password) VALUES ('user', 'mail', '$2a$10$Okc5IewNby5pbDOEHL04NuKKZWb2qdfOvff9CdZ2Lt5YeECzPYdpu');
INSERT INTO users (username, email, password) VALUES ('admin', 'mail2', '$2a$10$nPu/z1t2UkRE0UtbYi4KguEPtpOeAVVjqMw1KeR1yOt68e9/3yVPS');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1); -- user has role USER
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2); -- admin has role ADMIN
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1); -- admin has role USER