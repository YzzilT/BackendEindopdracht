INSERT INTO Roles (rolename)
values('admin'),
values('medewerker'),
values('reperateur');

-- password = lizzytelford
INSERT INTO Users (firstName, lastName,password,email,role_id,jwt)
VALUES ('lizzy', 'telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','asd@gmail.com',1,NULL),
VALUES ('lizzy', 'telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','asd@gmail.com',2,NULL),
VALUES ('lizzy', 'telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','asd@gmail.com',2,NULL),
VALUES ('lizzy', 'telford', '$2a$12$7Wz.Q0wZdkZsuds36eNhy.1JP/2svvi6FQhZSeSfWADveqLzXDjBe','asd@gmail.com',3,NULL);