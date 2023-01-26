package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String INSERT_SQL =
            """
                    INSERT developers_tbl(id, firstName, lastName, specialtyId, statusId) 
                    VALUES (?, ?, ?, ?, ?, ?)""";
    private static final String SELECT_BY_ID = "select id, firstName, lastName, skillsId, specialtyId, statusId " +
            "from developer_tbl where id = ?";
    private static final String SELECT_ALL = " select * from developers_tbl ";

    private static final String DELETE_SQL = "delete from developer_tbl where id = ?;";
    private static final String UPDATE_SQL = "update developer_tbl set firstName = ?, lastName = ?, " +
            "skillsId = ?, specialtyId = ?, statusId = ? where id = ?;";


    @Override
    public List<Developer> getAll(Status status) {
        String selectStatement;
        if (status == null){
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL+ " where statusId = " + status.getId();
        }

        List<Developer> itemList = new LinkedList<>();
        SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
        SkillRepositoryImpl skillRepository = new SkillRepositoryImpl();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();
            HashSet<Skill> skills;
            while (rs.next()) {
                long id = rs.getLong("id");
                String firstName = rs.getString("firstName");
                String secondName = rs.getString("secondName");
                long specialtyId = rs.getLong("specialtyId");
                int statusId = rs.getInt("statusId");

                skills = skillRepository.getSkillsFromLinkTable(id);

                itemList.add(new Developer(id, firstName, secondName, skills,
                        specialtyRepository.getById(specialtyId), Status.getStatusById(statusId) ));
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
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
        return null;
    }

    @Override
    public void addOrUpdate(Developer item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            insert(item);
        }else {
           //*** update ***
            update(item);
        }
    }


    public void insert(Developer item){

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getFirstName());
            preparedStatement.setString(2, item.getLastName());
            preparedStatement.setLong(3, item.getSpecialty().getId());
            preparedStatement.setInt(4, item.getStatus().getId());

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

    public void update(Developer item){

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

}
