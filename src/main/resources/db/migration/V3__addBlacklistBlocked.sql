CREATE TABLE IF NOT EXISTS blacklistblocked (
         id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
         cpf varchar(11) NOT NULL,
         status varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;