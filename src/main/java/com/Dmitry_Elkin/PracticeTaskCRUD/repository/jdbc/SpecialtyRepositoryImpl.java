package com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.utils.JdbcUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    private static final String INSERT_SQL =
            """
                    INSERT specialty_tbl(name, statusId)
                    VALUES (?, ?)""";
    private static final String SELECT_ALL = "select * from specialty_tbl";

    private static final String UPDATE_SQL = "update specialty_tbl set name = ?, statusId = ? where id = ?;";


    @Override
    public List<Specialty> getAll(Status status) {
        String selectStatement;
        if (status == null) {
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL + " where statusId = " + status.getId();
        }

        List<Specialty> itemList = new LinkedList<>();
        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Specialty(id, name, statusId));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
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
        String selectStatement = SELECT_ALL + " where id = " + itemId;
        List<Specialty> itemList = new LinkedList<>();
        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Specialty(id, name, statusId));
            }
            if (itemList.size() > 0) {
                return itemList.get(0);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;
    }

    @Override
    public void addOrUpdate(Specialty item) {
        //*** add ***
        if (item.getId() <= 0) {
//            item.setNewId();
            insert(item);
        } else {
            //*** update ***
            update(item);
        }
    }


    public void insert(Specialty item) {
        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getStatus().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
//                rowInserted = true;
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }

    public void update(Specialty item) {
        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getStatus().getId());
            statement.setLong(3, item.getId());
            statement.executeUpdate();

//            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
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


}
