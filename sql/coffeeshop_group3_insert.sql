
USE CoffeeShop_Group3;

-- BEAN data
INSERT INTO BEAN
VALUES
('B001', 'Colombian Roast', 'Medium', '2026-05-01', 'Colombia', 'Chocolate Notes'),
('B002', 'French Roast', 'Dark', '2026-05-10', 'France', 'Smoky Flavor');

-- CLIENTE data
INSERT INTO CLIENTE
VALUES
('C001', 'Mia Longo', 'M100', 'Medium', 'Latte'),
('C002', 'Khanh Haines', 'M101', 'Dark', 'Espresso'),
('C003', 'Huda Hussain', 'M102', 'Light', 'Cappuccino'),
('C004', 'Tyler Prim', NULL, 'Medium', 'Cold Brew');

-- DRINK data
INSERT INTO DRINK
VALUES
('D001', 'Latte', 'Coffee', 'Vanilla', 4.50),
('D002', 'Espresso', 'Coffee', 'Bold', 3.00);

-- ORDER data
INSERT INTO DRINK_ORDER
VALUES
('O001', '2026-06-20', 'C001'),
('O002', '2026-06-20', 'C002');

-- ADD_ON data
INSERT INTO ADD_ON
VALUES
('A001', 'Whipped Cream', 'Topping', 0.50),
('A002', 'Extra Shot', 'Espresso', 1.00);

-- USED_IN relationship
INSERT INTO USED_IN
VALUES
('B001', 'D001'),
('B002', 'D002');

-- HAS_ADD_ON relationship
INSERT INTO HAS_ADD_ON
VALUES
('D001', 'A001'),
('D001', 'A002'),
('D002', 'A002');

-- CONTAINS relationship
INSERT INTO CONTAINS
VALUES
('O001', 'D001', 1),
('O002', 'D002', 2);

-- RATING data
INSERT INTO RATING
VALUES
('R001', 5, 'Great drink!', '2026-06-20', 'D001', 'C001', 'O001'),
('R002', 4, 'Strong coffee.', '2026-06-20', 'D002', 'C002', 'O002');