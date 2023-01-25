package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DeveloperRepositoryImpl_ implements DeveloperRepository {


    private static final String INSERT_SQL =
            """
                    INSERT developer_db.developer_tbl(id, firstName, lastName, skillsId, specialtyId, statusId) 
                    VALUES (?, ?, ?, ?, ?, ?)""";
    private static final String SELECT_BY_ID = "select id, firstName, lastName, skillsId, specialtyId, statusId from developer_tbl where id = ?";
    private static final String SELECT_ALL = "select * from developers_tbl";
    private static final String DELETE_SQL = "delete from developer_tbl where id = ?;";
    private static final String UPDATE_SQL = "update developer_tbl set firstName = ?, lastName = ?, " +
            "skillsId = ?, specialtyId = ?, statusId = ? where id = ?;";


    @Override
    public List<Developer> getAll(Status status) {
        List<Developer> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("author");
                int skillsId = rs.getInt("skillsId");
                int specialtyId = rs.getInt("specialtyId");
                int statusId = rs.getInt("statusId");

//                itemList.add(new Developer(id, firstName, lastName, skills, specialty));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return itemList;
    }
    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Developer> getAll() {
        return getAll(null);
    }

    @Override
    public Developer getById(Long id) {
        try  {
        } catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("some io exception in module GsonDeveloperRepositoryImpl in meth getById: "+e.getMessage());
        }
        return null;
    }

    @Override
    public void addOrUpdate(Developer item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        }else {
           //*** update ***
            update(item);
        }
    }


    public void add(Developer item){
        try {
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("some io exception in module GsonDeveloperRepositoryImpl in meth add: "+e.getMessage());
        }
    }

    public void update(Developer item){
        try
        {
        } catch (Exception e) {
            System.out.println("FileNotFoundException in module GsonDeveloperRepositoryImpl in meth update: " + e.getMessage());
        }
    }

    @Override
    public void delete(Developer item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Developer item) {
        item.setUnDeleted();
        update(item);
    }


    private static void printSQLException(SQLException ex) {
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
