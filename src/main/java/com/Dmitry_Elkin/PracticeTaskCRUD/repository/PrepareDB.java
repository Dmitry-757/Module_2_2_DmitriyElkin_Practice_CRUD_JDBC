package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDB {
    public static void prepareBase() {
        String PREPARE_QUERY =
                "DROP DATABASE IF EXISTS vinyla_db;"
                ;
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate(PREPARE_QUERY);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        PREPARE_QUERY =
                        "CREATE DATABASE IF NOT EXISTS vinyla_db;"
                ;
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate(PREPARE_QUERY);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void createTables(){
        String CREATE_TABLE_vinyla_tbl =
                """
                          CREATE TABLE vinyla_db.vinyla_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          author VARCHAR(100) NOT NULL,
                          year INT NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);
                """;



        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.addBatch(CREATE_TABLE_vinyla_tbl);

            statement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void fillVinyla(){
        String fillStr1 =
                "INSERT vinyla_db.vinyla_tbl(name, author, year) \n" +
                        "VALUES (?, ?, ?)";
//                        "VALUES ('Dell', 'USA', 10000, 'Manufacturer of Dell noteBooks','just logo');";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);

            ps.setString(1, "Time");
            ps.setString(2, "Hans Zimmer");
            ps.setInt(3, 2020);
            ps.addBatch();

            ps.setString(1, "Waterloo");
            ps.setString(2, "ABBA");
            ps.setInt(3, 1974);
            ps.addBatch();


            int[] rows = ps.executeBatch();
            System.out.println("to vinyla_db.vinyla_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException  e) {
            e.printStackTrace();
        }

    }



    public static void fillTables(){
        fillVinyla();
    }
}
