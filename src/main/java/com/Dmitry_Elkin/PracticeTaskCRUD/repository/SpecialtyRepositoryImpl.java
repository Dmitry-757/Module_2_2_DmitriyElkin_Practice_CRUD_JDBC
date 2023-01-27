package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
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
//    private static final String SELECT_ALL = """
//            select sp.id, sp.name, st.status_value from specialty_tbl as sp
//            left join status_tbl as st\s
//            on sp.statusId = st.id""";
private static final String SELECT_ALL = "select * from specialty_tbl";

    //    private static final String DELETE_SQL = "delete from specialty_tbl where id = ?;";
    private static final String UPDATE_SQL = "update specialty_tbl set name = ?, statusId = ? where id = ?;";


    @Override
    public List<Specialty> getAll(Status status) {
        String selectStatement;
        if (status == null){
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL + " where statusId = " + status.getId();
        }

        List<Specialty> itemList = new LinkedList<>();
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Specialty(id, name, statusId));
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
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
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Specialty(id, name, statusId));
            }
            if (itemList.size()>0){
                return itemList.get(0);
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
        }
        return null;
    }

    @Override
    public void addOrUpdate(Specialty item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            insert(item);
        }

        //*** update ***
        update(item);
    }


    public void insert(Specialty item){
        Connection connection = DBConnection.getConnection();
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
            StatusRepository.printSQLException(e);
        }
    }

    public void update(Specialty item){
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getStatus().getId());
            statement.executeUpdate();

//            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
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
