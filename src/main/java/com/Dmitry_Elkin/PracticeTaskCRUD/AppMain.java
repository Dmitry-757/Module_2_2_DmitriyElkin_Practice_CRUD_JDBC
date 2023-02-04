package com.Dmitry_Elkin.PracticeTaskCRUD;


import com.Dmitry_Elkin.PracticeTaskCRUD.view.MainView;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.DBConnection;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class AppMain {

    public static void main(String[] args) {



        System.out.println("Start...");
        Connection connection = DBConnection.getConnection();
        Database database;
        try {
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        Liquibase liquibase = new liquibase.Liquibase("db/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);

        try {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

        MainView cli = new MainView();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nstarting...");
        cli.upLevelMenu();

    }
}
