Coffee Database Application
SER322 Group 3

Team Members
------------
- Mia Longo
- Khanh Haines
- Tyler Prim
- Huda Hussain

Project Description
-------------------
This project is a Java command line application that connects to a MySQL coffee shop database.
The application allows users to search, insert, update, and delete information from the database using a menu-driven interface.

Requirements
------------
- Java JDK 21 or newer
- Gradle 8.13
- MySQL Server 8.x
- MySQL Workbench (recommended)

Project Structure
-----------------
src/
    main/
        java/
            coffee/

sql/
    coffeeshop_group3_create.sql
    coffeeshop_group3_insert.sql
    coffeeshop_group3_queries.sql

Creating the Database
---------------------
1. Make sure MySQL is installed and running.

2. Open a terminal and log into MySQL:
      mysql -u root -p

3. Create the project database:
      CREATE DATABASE CoffeeShop_Group3;

4. Exit MySQL:
      EXIT;

5. From the project root directory, create the database tables:
      mysql -u root -p CoffeeShop_Group3 < sql/coffeeshop_group3_create.sql

6. Load the sample data:
      mysql -u root -p CoffeeShop_Group3 < sql/coffeeshop_group3_insert.sql

Building the Project
--------------------
From the project root directory, build the project:
      ./gradlew clean build

Running the Application
-----------------------
Run the application from the project root:
      ./gradlew run --console=plain

When prompted, enter your MySQL connection information.

Database URL:
      jdbc:mysql://localhost:3306/CoffeeShop_Group3

Username:
      Your local MySQL username
      (example: root)

Password:
      Your local MySQL password

After connecting successfully, the application will display the main menu where you can access the Cliente, Drink, Bean, and Order modules.

Video
-----
Video URL:
(TBD)

Individual Contributions
------------------------
Mia Longo (mclongo)
- Project setup, Git repository setup, Gradle configuration
- README and setup documentation
- Main.java
- Menu.java
- Database integration: Integrated Drink, Cliente, Bean, and Order modules
- Testing: MySQL connectivity and application functionality

Khanh Haines (dthaine1)
- DBConnection.java
- Cliente.java
- Rating.java
- Project structure
- Filming/coordinating the video

Tyler Prim (tprim)
- Bean.java
- Order.java

Huda Hussain (hahussa4)
- Drink.java
- Script for intro, conclusion, and my part of the code for the video
- Testing: MySQL connectivity and application functionality

Notes
-----
- Use the SQL scripts in the order listed above when setting up the database.
- The application is built using Gradle and Java 21.
