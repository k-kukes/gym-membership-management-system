package org.example;

import java.sql.*;

public class DatabaseManager {
    //============Connect to SQLite JDBC driver===========
    public static Connection connect(){
        String Base_Path = "jdbc:sqlite:src/main/resources/database/";
        String DB_Path = Base_Path + "data.db";

        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_Path);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

        return connection;
    }

    //============Create a table===========
    public static void createNewTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS users(
                userId INTEGER PRIMARY KEY,
                loginUser VARCHAR(30) UNIQUE,
                loginPassword VARCHAR(30) NOT NULL,
                firstName VARCHAR(20) NOT NULL,
                lastName VARCHAR(20) NOT NULL,
                dob date,
                phoneNo VARCHAR(12) NOT NULL,
                address VARCHAR(50)
                )
                """;
    }
}
