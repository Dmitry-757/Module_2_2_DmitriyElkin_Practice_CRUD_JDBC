package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;


import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    private static final String INSERT_SQL =
            """
                    INSERT skills_tbl(name, statusId)
                    VALUES (?, ?)""";
//    private static final String SELECT_ALL = """
//            select sp.id, sp.name, st.status_value from skills_tbl as sp
//            left join status_tbl as st\s
//            on sp.statusId = st.id""";

    private static final String SELECT_ALL = "select * from skills_tbl";

    //    private static final String DELETE_SQL = "delete from skills_tbl where id = ?;";
    private static final String UPDATE_SQL = "update skills_tbl set name = ?, statusId = ? where id = ?;";

    public long getStatusId(Status item){
        long statusId = 0;
        String selectStatement = "select * from status_tbl where status_value = "+item.getStatusValue();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                statusId = rs.getLong("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return statusId;
    }


    @Override
    public List<Skill> getAll(Status status) {
        String selectStatement;
        if (status == null){
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL + " where statusId = " + getStatusId(status);
        }

        List<Skill> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int status_value = rs.getInt("status_value");
                itemList.add(new Skill(id, name, status_value));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return itemList;
    }


    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Skill> getAll() {
        return getAll(null);
    }



    @Override
    public Skill getById(Long itemId) {
        String selectStatement = SELECT_ALL + " where id = " + itemId;
        List<Skill> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int status_value = rs.getInt("status_value");
                itemList.add(new Skill(id, name, status_value));
            }
            if (itemList.size()>0){
                return itemList.get(0);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public HashSet<Skill> getSkillsFromLinkTable(long developerId){
        HashSet<Skill> itemSet = new HashSet<>();
        String selectStatement = " select * from developer2skills_tbl where developerId = " + developerId;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                long skillId = rs.getLong("skillId");

                itemSet.add(getById(skillId));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return itemSet;
    }

    @Override
    public void addOrUpdate(Skill item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            insert(item);
        } else {
            //*** update ***
            update(item);
        }
    }


    public void insert(Skill item) {
        long statusId = getStatusId(item.getStatus());

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

    public void update(Skill item) {
        long statusId = getStatusId(item.getStatus());

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
    public void delete(Skill item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Skill item) {
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