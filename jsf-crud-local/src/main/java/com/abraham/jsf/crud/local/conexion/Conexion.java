package com.abraham.jsf.crud.local.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/crud_empleados?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "sasa1234";

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Podrías lanzar una RuntimeException si quieres que falle rápido
            throw new SQLException("No se pudo cargar el driver JDBC", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
