INSERT INTO client (id,name,email,cpf) VALUES (1,"victor","victor@gmail.com","02160795607");
INSERT INTO client (id,name,email,cpf) VALUES (2,"howard","howard@gmail.com","41076018033");
INSERT INTO account (num_acc,agency,number_acc,holder_id,balance,active) VALUES (1,"001","11",1,0.0,true);
INSERT INTO account (num_acc,agency,number_acc,holder_id,balance,active) VALUES (2,"001","12",2,100.0,true);
INSERT INTO operations (id,date,type_op,value,account_num_acc) VALUES (1,"2020-01-23 16:24:01","DEPOSIT",20.00,1);
INSERT INTO operations (id,date,type_op,value,account_num_acc) VALUES (2,"2020-01-23 16:24:01","WITHDRAW",2.00,2);