package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    private static final String INSERT_SQL =
            """
                    INSERT specialty_tbl(name, statusId)
                    VALUES (?, ?)""";
    private static final String SELECT_BY_ID = "select id, name, statusId from specialty_tbl where id = ?";
    private static final String SELECT_ALL = """
            select sp.id, sp.name, st.status_value from specialty_tbl as sp
            left join status_tbl as st\s
            on sp.statusId = st.id""";
    private static final String DELETE_SQL = "delete from specialty_tbl where id = ?;";
    private static final String UPDATE_SQL = "update specialty_tbl set name = ?, statusId = ? where id = ?;";
    @Override
    public List<Specialty> getAll(Status status) {
        String selectStatement;
        if (status == null){
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL + " where st.status_value = " + status.getStatusValue();
        }

        List<Specialty> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int status_value = rs.getInt("status_value");
                itemList.add(new Specialty(id, name, status_value));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return itemList;
    }

    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Specialty> getAll() {
        return getAll(null);
    }

    @Override
    public Specialty getById(Long id) {

        return null;
    }

    @Override
    public void addOrUpdate(Specialty item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        }

        //*** update ***
        update(item);
    }


    public void add(Specialty item){
    }

    public void update(Specialty item){

    }

    @Override
    public void delete(Specialty item) {
        item.setDeleted();
        update(item);
    }

    @Override
    public void unDelete(Specialty item) {
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
