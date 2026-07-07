CREATE DATABASE IF NOT EXISTS CoffeeShop_Group3;
USE CoffeeShop_Group3;

-- BEAN table
CREATE TABLE BEAN (
    BeanID VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    RoastType VARCHAR(50) NOT NULL,
    RoastDate DATE NOT NULL,
    Origin VARCHAR(50) NOT NULL,
    FlavorNotes VARCHAR(255),
    PRIMARY KEY (BeanID)
);

-- CLIENT table
CREATE TABLE CLIENTE (
    ClientID VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    MemberID VARCHAR(50),
    PreferredRoast VARCHAR(50),
    PreferredDrink VARCHAR(50),
    PRIMARY KEY (ClientID)
);

-- Drink
CREATE TABLE DRINK (
    DrinkID VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Flavor VARCHAR(50),
    Price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (DrinkID)
);

-- ORDER table
CREATE TABLE DRINK_ORDER(
    OrderID VARCHAR(50) NOT NULL,
    Date DATE NOT NULL,
    ClientID VARCHAR(50) NOT NULL,
    PRIMARY KEY (OrderID),
    FOREIGN KEY (ClientID) REFERENCES CLIENTE(ClientID)
);

-- ADD_ON table
CREATE TABLE ADD_ON (
    AddOnID VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Cost DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (AddOnID)
);

-- RATING table
CREATE TABLE RATING (
    RatingID VARCHAR(50) NOT NULL,
    Value INT NOT NULL,
    Comment VARCHAR(255),
    Date DATE NOT NULL,
    DrinkID VARCHAR(50) NOT NULL,
    ClientID VARCHAR(50) NOT NULL,
    OrderID VARCHAR(50) NOT NULL,
    PRIMARY KEY (RatingID),
    CONSTRAINT chk_rating_value CHECK (Value BETWEEN 1 AND 5),
    FOREIGN KEY (DrinkID) REFERENCES DRINK(DrinkID),
    FOREIGN KEY (ClientID) REFERENCES CLIENTE(ClientID),
    FOREIGN KEY (OrderID) REFERENCES DRINK_ORDER(OrderID)
);

-- Junction tables here
    -- CONTAINS: ORDER -> DRINK (an order contains many drinks, a drink can be in many orders)
CREATE TABLE CONTAINS (
    OrderID VARCHAR(50) NOT NULL,
    DrinkID VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL DEFAULT 1,
    PRIMARY KEY (OrderID, DrinkID),
    FOREIGN KEY (OrderID) REFERENCES DRINK_ORDER(OrderID),
    FOREIGN KEY (DrinkID) REFERENCES DRINK(DrinkID)
);

-- HAS_ADD_ON: DRINK -> ADD_ON (a drink can have many add-ons, an add-on can apply to many drinks)
CREATE TABLE HAS_ADD_ON (
    DrinkID VARCHAR(50) NOT NULL,
    AddOnID VARCHAR(50) NOT NULL,
    PRIMARY KEY (DrinkID, AddOnID),
    FOREIGN KEY (DrinkID) REFERENCES DRINK(DrinkID),
    FOREIGN KEY (AddOnID) REFERENCES ADD_ON(AddOnID)
);

-- USED_IN: BEAN -> DRINK (a bean can be used in many drinks, a drink can use many beans)
CREATE TABLE USED_IN (
    BeanID VARCHAR(50) NOT NULL,
    DrinkID VARCHAR(50) NOT NULL,
    PRIMARY KEY (BeanID, DrinkID),
    FOREIGN KEY (BeanID) REFERENCES BEAN(BeanID),
    FOREIGN KEY (DrinkID) REFERENCES DRINK(DrinkID)
);
