package com.abraham.jsf.crud.local.conexion;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/crud_empleados?useSSL=false&serverTimezone=UTC";
    // private static final String URL =
    // "jdbc:mysql://localhost:3306/crud_empleados?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = "root";
    private static final String PASS = "sasa1234"; // <-- usá un archivo externo para esto en producción

    public static Connection getConexion() throws SQLException {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }
}