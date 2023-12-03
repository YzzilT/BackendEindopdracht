INSERT INTO Roles (role_name)
values('frontdesk'),
      ('warehouse'),
      ('customer');

-- password = lizzytelford
INSERT INTO Users (first_name, last_name,password,email,role_id,jwt)
VALUES ('Lizzy', 'Telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','lizzytelford@gmail.com',1,NULL),
       ('John', 'Doe', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','johndoe@gmail.com',2,NULL),
       ('Winston', 'Kat', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','winstonkat@gmail.com',3,NULL);

INSERT INTO Invoices(customer_name, invoice_date, total_amount, email, order_id, invoice_number)
VALUES('Lizzy Telford', '10/28/2023', 500, 'lizzytelford@gmail.com', 1, 1);

INSERT INTO Orders(customer_name, total_amount, invoice_invoice_number, user_id, id)
VALUES (' Lizzy Telford', '100', 1, 1, 1);


INSERT INTO Products (name, price, original_stock, current_stock, description, product_type, order_id)
VALUES('latex gloves', 20.00, 100, 100, 'one size fits all latex gloves', 'GLOVES', 1),
('X-Ray machine 3000', 5000.00, 5, 3, 'Our newest x-ray machine fitted for all hospitals', 'XRAYMACHINE', 1),
('needles 0,5 mm', 20.00, 50, 36, 'needles perfect for sedation', 'NEEDLES', 1),
('bandage 8 cm',10.00, 100, 100, 'waterresistant bandages', 'BANDAGE', 1);

INSERT INTO Repairs (user_id, problem_description, product_id, file_id, picture, submission_date)
VALUES (1, 'My xray machine is broken, can you come and fix it?', 1, 0, 1, '10/28/2023'),
       (1, 'I am not happy with my gloves, can you replace them?', 1, 0, 1, '10/28/2023');


INSERT INTO order_line ( quantity, total_amount, order_id, product_id)
VALUES(1, 20.00, 1, 1),
      (1, 5000, 1, 2),
      (1, 20.00, 1, 3),
      (1, 10.00, 1,4);






