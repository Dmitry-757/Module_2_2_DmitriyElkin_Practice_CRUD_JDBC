package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String INSERT_SQL =
            """
                    INSERT developers_tbl(firstName, lastName, specialtyId, statusId) 
                    VALUES (?, ?, ?, ?)""";
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
    public Developer getById(Long itemId) {
        String selectStatement = SELECT_ALL+ " where id = " + itemId;

        SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
        SkillRepositoryImpl skillRepository = new SkillRepositoryImpl();

        HashSet<Skill> skills;
        skills = skillRepository.getSkillsFromLinkTable(itemId);

        List<Developer> itemList = new LinkedList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String firstName = rs.getString("firstName");
                String secondName = rs.getString("lastName");
                long specialtyId = rs.getLong("specialtyId");
                int statusId = rs.getInt("statusId");
                Status status = Status.getStatusById(statusId);
                Specialty specialty = specialtyRepository.getById(specialtyId);

                itemList.add(new Developer(id, firstName, secondName, skills,
                        specialty, status ));
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
        }
        if (itemList.size()>0){
            return itemList.get(0);
        } else {
            return null;
        }

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
            connection.setAutoCommit(false);

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
            //let`s write skills to bd
            try( PreparedStatement ps = connection.prepareStatement("INSERT developer2skills_tbl(developerId, skillId) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                for (Skill skill : item.getSkills()) {
                    ps.setLong(1, item.getId());
                    ps.setLong(2, skill.getId());
                    ps.addBatch();
                }
                int[] rows = ps.executeBatch();
                System.out.println("to developer2skills_tbl where added " + (rows.length) +" record(s)");
            } catch (SQLException e) {
                StatusRepository.printSQLException(e);
            }

            connection.commit();

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
