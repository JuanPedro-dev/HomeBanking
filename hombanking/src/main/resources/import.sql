
INSERT INTO client (first_name, last_name, email) VALUES ('Melba', 'Morel', 'melba@mindhub.com');
INSERT INTO client (first_name, last_name, email) VALUES ('Irene', 'Saenz', 'irene@mindhub.com');
INSERT INTO account (number, creation_date, balance, client_id) VALUES ('VIN001', '2022-09-08', 5000, 1L);
INSERT INTO account (number, creation_date, balance, client_id) VALUES ('VIN002', '2022-09-08', 7500, 1L);



INSERT INTO account (number, creation_date, balance, client_id) VALUES ('VIN003', '2022-09-08', 9000, 2L);
INSERT INTO account (number, creation_date, balance, client_id) VALUES ('VIN004', '2022-09-08', 6500, 2L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,2000,'transferencia recibida','2022-09-25',1L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,4000,'Compra tienda xx','2022-09-25',1L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,1000,'transferencia recibida','2022-09-25',2L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,200,'Compra tienda xy','2022-09-25',2L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,8000,'transferencia recibida','2022-09-25',3L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,2000,'Compra tienda xz','2022-09-25',3L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,700,'transferencia recibida','2022-09-25',4L);
INSERT INTO transactions (type,amount,description,creation_date,account_id) VALUES (0,2000,'Compra tienda xi','2022-09-25',4L);



INSERT INTO loan (name,max_amount) VALUES  ('Hipotecario',500000);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (1L,12);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (1L,24);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (1L,36);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (1L,48);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (1L,60);
INSERT INTO loan (name,max_amount) VALUES  ('Personal',100000);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (2L,6);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (2L,12);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (2L,24);
INSERT INTO loan (name,max_amount) VALUES  ('Automotriz',300000);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (3L,6);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (3L,12);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (3L,24);
INSERT INTO loan_payments   (loan_id,payments)  VALUES  (3L,36);
INSERT INTO client_loan (amount,payments,client_id,loan_id) VALUES (400000,60,1L,1L);
INSERT INTO client_loan (amount,payments,client_id,loan_id) VALUES (50000,12,1L,2L);
INSERT INTO client_loan (amount,payments,client_id,loan_id) VALUES (100000,24,2L,2L);
INSERT INTO client_loan (amount,payments,client_id,loan_id) VALUES (200000,36,2L,3L);