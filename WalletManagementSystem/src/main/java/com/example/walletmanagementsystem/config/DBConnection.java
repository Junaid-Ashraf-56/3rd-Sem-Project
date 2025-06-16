package com.example.walletmanagementsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//psql -U trading -d smart_trading_system

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection==null){
            try{
                   Class.forName("org.postgresql.Driver");
                   connection = DriverManager.getConnection(
                           "jdbc:postgresql://localhost:5432/smart_trading_system",
                           "trading",
                           "Str0ngPass@7733"
                   );
                System.out.println("PostgreSQL Database Connected Successfully");
            }catch (ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void closeConnection(){
        if (connection!=null){
            try {
                connection.close();
                System.out.println("Database Connection close");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}



