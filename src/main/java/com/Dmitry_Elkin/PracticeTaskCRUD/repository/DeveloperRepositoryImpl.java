package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String INSERT_SQL =
            """
                    INSERT developer_db.developer_tbl(id, firstName, lastName, skillsId, specialtyId, statusId) 
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
            selectStatement = SELECT_ALL+ " where statusId = " + StatusRepository.getStatusId(status);
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
                long statusId = rs.getLong("statusId");

                skills = skillRepository.getSkillsFromLinkTable(id);

                itemList.add(new Developer(id, firstName, secondName, skills,
                        specialtyRepository.getById(specialtyId), StatusRepository.getById(statusId) ));
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
            add(item);
        }else {
           //*** update ***
            update(item);
        }
    }


    public void add(Developer item){
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
