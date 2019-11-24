package Servidor;

import java.sql.*;

class ConnFactory {

    public Connection getConnection() throws SQLException {
        try {

            Class.forName("org.postgresql.Driver");
            Connection conn;
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/banco?user=postgres&password=admin");
            return conn;

        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void runSQL() {

    }
}