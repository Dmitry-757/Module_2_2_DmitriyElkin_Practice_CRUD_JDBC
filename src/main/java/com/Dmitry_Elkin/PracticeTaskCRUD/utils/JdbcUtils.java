package com.Dmitry_Elkin.PracticeTaskCRUD.utils;

import java.sql.*;

public class JdbcUtils {
    private static String username = "root";
    private static String password = "dingo1975";
//    private static String URL = "jdbc:mysql://localhost:3306";
    private static String URL = "jdbc:mysql://localhost:3306/proselyte_developers_db";
    public static Connection connectionExp;


    static {
        try {
            //для подключения  mySQL версии выше 8.0 используем драйвер "com.mysql.cj.jdbc.Driver", а ниже "com.mysql.jdbc.Driver"
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
        } catch ( ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try
        {
            Connection connection = DriverManager.getConnection(URL, username, password);
            if (connection != null) System.out.println("Connection Successful !\n");
            if (connection == null) System.exit(0);

            connectionExp = connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        //if connection is closed - lets open it again
        try {
            if (connectionExp.isClosed() ) {
                connectionExp = DriverManager.getConnection(URL, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionExp;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatement(String sql, int parameterIndex) throws SQLException {
        return getConnection().prepareStatement(sql, parameterIndex);
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


}
