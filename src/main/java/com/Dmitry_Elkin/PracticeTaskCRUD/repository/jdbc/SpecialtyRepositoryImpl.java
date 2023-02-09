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

        String sql;
        if (status == null){
            sql = SELECT_ALL;
        } else {
            sql = SELECT_ALL + " where statusId = " + status.getId();
        }

        List<Specialty> itemList = new LinkedList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itemList.add(convertResultSetToSpecialty(rs));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        return itemList;
    }

    @Override
    public List<Specialty> getAll() {
        return getAll(null);
    }

    @Override
    public Specialty getById(Long itemId) {
        String sql = SELECT_ALL + " where id = " + itemId;
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return convertResultSetToSpecialty(rs);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;
    }

    @Override
    public Specialty insert(Specialty item) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getStatus().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return getById(item.getId());
    }

    public Specialty update(Specialty item) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getStatus().getId());
            preparedStatement.setLong(3, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return getById(item.getId());
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

    private Specialty convertResultSetToSpecialty(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        int statusId = rs.getInt("statusId");
        return new Specialty(id, name, statusId);
    }


}
