INSERT INTO client (id,name,email,cpf) VALUES (1,"Pedro","pedro@gmail.com","42511229846");
INSERT INTO client (id,name,email,cpf) VALUES (2,"Lucia","lucia@gmail.com","88804879653");
INSERT INTO account (num_acc,agency,number_acc,holder_id,balance,active) VALUES (1,"001","11",1,0.0,true);
INSERT INTO account (num_acc,agency,number_acc,holder_id,balance,active) VALUES (2,"001","12",2,100.0,true);
INSERT INTO operations (id,date,type_op,value,account_num_acc) VALUES (1,"2020-01-23 16:24:01","DEPOSIT",20.00,1);
INSERT INTO operations (id,date,type_op,value,account_num_acc) VALUES (2,"2020-01-23 16:24:01","WITHDRAW",2.00,2);