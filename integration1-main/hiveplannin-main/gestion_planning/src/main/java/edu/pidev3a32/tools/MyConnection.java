package edu.pidev3a32.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String url = "jdbc:mysql://localhost:3306/patchitchou"; // Ensure this is correct
    private String login = "root";
    private String pwd = "";
    private Connection cnx;

    private static MyConnection instance;

    // Private constructor to prevent instantiation
    private MyConnection() {
        connect();
    }

    // Method to establish a connection
    private void connect() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    public Connection getCnx() {
        if (cnx == null || !isConnectionValid()) {
            connect(); // Reconnect if the connection is closed
        }
        return cnx;
    }

    // Method to check if the connection is valid
    private boolean isConnectionValid() {
        try {
            return cnx != null && !cnx.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnection() {
        if (cnx != null) {
            try {
                cnx.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            } finally {
                cnx = null; // Set to null to indicate it's closed
            }
        }
    }
}