package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import java.sql.Connection;

public class DBConnection {
    public static Connection getConnection(){
//        return MSSQLConnection.getConnection();
        return mySQLConnection.getConnection();
    }
}
