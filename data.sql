INSERT INTO Roles (rolename)
values('frontdesk'),
values('warehouse'),
values('customer');

-- password = lizzytelford
INSERT INTO Users (firstName, lastName,password,email,role_id,jwt)
VALUES ('Lizzy', 'Telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','lizzytelford@gmail.com',1,NULL),
VALUES ('John', 'Doe', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','johndoe@gmail.com',2,NULL),
VALUES ('Winston', 'Kat', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','winstonkat@gmail.com',3,NULL);

INSERT INTO Products (name, price, originalStock, currentStock, description, productType, order_id, orderLines)
VALUES('latex gloves', 20.00, 100, 100, 'one size fits all latex gloves', GLOVES, 1),
VALUES('X-Ray machine 3000', 5000.00, 5, 3, 'Our newest x-ray machine fitted for all hospitals', XRAYMACHINE, 2),
VALUES('needles 0,5 mm', 20.00, 50, 36, 'needles perfect for sedation', NEEDLES, 3),
VALUES('bandage 8 cm' 10.00, 100, 100, 'waterresistant bandages', BANDAGE, 4);

INSERT INTO Repairs (user_id, problemDescription, product_id, file_Id, picture)
VALUES( 1, 'My xray machine is broken, can you come and fix it?', 1, null, 'picture'),
VALUES(2, 'I am not happy with my gloves, can you replace them?', 2, null, 'picture');


INSERT INTO OrderLine( quantity, totalAmount, order, product_id)
VALUES (1, 20.00, 1, 1),
VALUES (50, 20.00, 2, 2);

INSERT INTO Orders(customerName, totalAmount)
VALUES (' Lizzy Telford', );

INSERT INTO Invoices(customerName, invoiceDate, totalAmount, email, order_id)
VALUES("Lizzy Telford", '10/28/2023', 500, 'lizzytelford@gmail.com', 1);


