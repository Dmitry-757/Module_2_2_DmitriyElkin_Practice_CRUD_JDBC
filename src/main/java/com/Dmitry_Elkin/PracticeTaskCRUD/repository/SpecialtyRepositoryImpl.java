package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    private static final String INSERT_SQL =
            """
                    INSERT specialty_tbl(name, statusId)
                    VALUES (?, ?)""";
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
    public Specialty getById(Long itemId) {
        String selectStatement = SELECT_ALL + " where sp.id = " + itemId;
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
            if (itemList.size()>0){
                return itemList.get(0);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
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
        long statusId = 0;
        String selectStatement = "select * from status_tbl where status_value = "+item.getStatus().getStatusValue();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                statusId = rs.getLong("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        try (Connection connection = DBConnection.getConnection();


             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setLong(2, statusId);

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
//                rowInserted = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void update(Specialty item){
        long statusId = 0;
        String selectStatement = "select * from status_tbl where status_value = "+item.getStatus().getStatusValue();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                statusId = rs.getLong("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setLong(2, statusId);
            statement.setLong(3, item.getId());
            statement.executeUpdate();

//            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

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
