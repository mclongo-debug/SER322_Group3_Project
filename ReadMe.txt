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
    test/
        java/
            coffee/

sql/
    coffeeshop_group3_create.sql
    coffeeshop_group3_insert.sql
    coffeeshop_group3_queries.sql

Creating the Database
---------------------
1. Create a new MySQL database.
2. Open MySQL Workbench.
3. Run:
      sql/coffeeshop_group3_create.sql
4. Run:
      sql/coffeeshop_group3_insert.sql

Building the Project
--------------------
From the project root:

    ./gradlew build

Running the Application
-----------------------
From the project root:

    ./gradlew run

Testing
-------
Run all tests:

    ./gradlew test

Video
-----
Video URL:
(TBD)

Individual Contributions
------------------------
Mia Longo (mclongo)
- Project setup
- Gradle configuration
- Git repository setup
- README
- Main.java

Khanh Haines (dthaine1)
- 

Tyler Prim (tprim)
- 

Huda Hussain (hahussa4)
- 


Notes
-----
- Use the SQL scripts in the order listed above when setting up the database.
- The application is built using Gradle and Java 21.
