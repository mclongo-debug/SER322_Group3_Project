USE CoffeeShop_Group3;

-- 1. All drinks and their prices.
SELECT Name, Price
FROM DRINK;

-- 2. All beans that originate from Colombia.
SELECT BeanID, Name, RoastType, RoastDate, FlavorNotes
FROM BEAN
WHERE Origin = 'Colombia';

-- 3. All clients and their preferred drinks, ordered alphabetically.
SELECT Name, PreferredDrink
FROM CLIENTE
ORDER BY Name;

-- 4. All drinks that cost more than $4.00.
SELECT DrinkID, Name, Type, FLavor, Price 
FROM DRINK
WHERE Price > 4.00;

-- 5. All drinks and the beans used to prepare them.
SELECT DRINK.Name, BEAN.Name
FROM DRINK
INNER JOIN USED_IN
ON DRINK.DrinkID = USED_IN.DrinkID
INNER JOIN BEAN
ON USED_IN.BeanID = BEAN.BeanID;

-- 6. All drinks and their available add-ons.
SELECT DRINK.Name, ADD_ON.Name
FROM DRINK
INNER JOIN HAS_ADD_ON
ON DRINK.DrinkID = HAS_ADD_ON.DrinkID
INNER JOIN ADD_ON
ON HAS_ADD_ON.AddOnID = ADD_ON.AddOnID;

-- 7. All clients and the orders they have placed.
SELECT CLIENTE.Name, DRINK_ORDER.OrderID, DRINK_ORDER.Date
FROM CLIENTE
LEFT JOIN DRINK_ORDER
ON CLIENTE.ClientID = DRINK_ORDER.ClientID;

-- 8. All orders and the client who placed each order.
SELECT DRINK_ORDER.OrderID,DRINK_ORDER.Date,CLIENTE.Name AS ClientName
FROM DRINK_ORDER
JOIN CLIENTE
ON DRINK_ORDER.ClientID = CLIENTE.ClientID;

-- 9. Number of orders placed by each client.
SELECT CLIENTE.Name, COUNT(DRINK_ORDER.OrderID)
FROM CLIENTE
LEFT JOIN DRINK_ORDER
ON CLIENTE.ClientID = DRINK_ORDER.ClientID
GROUP BY CLIENTE.ClientID, CLIENTE.Name;

-- 10. Average rating for each drink.
SELECT DRINK.Name, AVG(RATING.Value)
FROM DRINK
JOIN RATING
ON DRINK.DrinkID = RATING.DrinkID
GROUP BY DRINK.DrinkID, DRINK.Name;

-- 11. Total quantity of drinks in each order.
SELECT OrderID, SUM(Quantity)
FROM CONTAINS
GROUP BY OrderID;

-- 12. All clients who have placed more than one order.
SELECT CLIENTE.Name, COUNT(DRINK_ORDER.OrderID)
FROM CLIENTE
JOIN DRINK_ORDER
ON CLIENTE.ClientID = DRINK_ORDER.ClientID
GROUP BY CLIENTE.ClientID, CLIENTE.Name
HAVING COUNT(DRINK_ORDER.OrderID) > 1;

-- 13. Names of all drinks that have never received a rating.
SELECT DRINK.Name
FROM DRINK
LEFT JOIN RATING
ON DRINK.DrinkID = RATING.DrinkID
WHERE RATING.RatingID IS NULL;

-- 14. Name and price of the most expensive drink.
SELECT Name, Price
FROM DRINK
ORDER BY Price DESC
LIMIT 1;