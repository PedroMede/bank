CREATE TABLE IF NOT EXISTS client (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    cpf varchar(11) NOT NULL,
    email varchar(50) NOT NULL,
    name varchar(100) DEFAULT NULL,
    UNIQUE KEY UK_cpf (cpf),
    UNIQUE KEY UK_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS account (
    num_acc bigint NOT NULL AUTO_INCREMENT,
    active boolean NOT NULL,
    agency varchar(50) NOT NULL,
    balance double DEFAULT NULL,
    number_acc varchar(50) DEFAULT NULL,
    holder_id bigint NOT NULL ,
    PRIMARY KEY (num_acc),
    FOREIGN KEY (holder_id) REFERENCES client(id) ,
    UNIQUE KEY UK_number_acc (number_acc)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS operations (
   id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
   type_op varchar(50) NOT NULL,
   value double DEFAULT NULL,
   account_num_acc bigint NOT NULL ,
   FOREIGN KEY (account_num_acc) REFERENCES account(num_acc)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS transfer (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  value double DEFAULT NULL,
  destiny_acc_num_acc bigint NOT NULL ,
  origin_acc_num_acc bigint NOT NULL ,
  FOREIGN KEY (destiny_acc_num_acc) REFERENCES account(num_acc),
  FOREIGN KEY (origin_acc_num_acc) REFERENCES account(num_acc)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;