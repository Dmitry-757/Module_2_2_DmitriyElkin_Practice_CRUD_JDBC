package com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.DeveloperRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.utils.JdbcUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String INSERT_SQL =
            """
                    INSERT developers_tbl(firstName, lastName, specialtyId, statusId)
                    VALUES (?, ?, ?, ?)""";
    private static final String SELECT_ALL_SQL = " select * from developers_tbl ";

    private static final String UPDATE_SQL = "update developers_tbl set firstName = ?, lastName = ?, " +
            "specialtyId = ?, statusId = ? where id = ?;";

    private final SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
    private final SkillRepositoryImpl skillRepository = new SkillRepositoryImpl();


    @Override
    public List<Developer> getAll(Status status) {
        String sql;
        if (status == null){
            sql = SELECT_ALL_SQL;
        } else {
            sql = SELECT_ALL_SQL + " where statusId = " + status.getId();
        }

        List<Developer> itemList = new LinkedList<>();

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            HashSet<Skill> skills;
            while (rs.next()) {
                long id = rs.getLong("id");
                String firstName = rs.getString("firstName");
                String secondName = rs.getString("lastName");
                long specialtyId = rs.getLong("specialtyId");
                int statusId = rs.getInt("statusId");

                skills = skillRepository.getSkillsByDeveloper(id);

                itemList.add(new Developer(id, firstName, secondName, skills,
                        specialtyRepository.getById(specialtyId), Status.getStatusById(statusId) ));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
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
        String selectStatement = SELECT_ALL_SQL + " where id = " + itemId;

        HashSet<Skill> skills = skillRepository.getSkillsByDeveloper(itemId);

        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(selectStatement)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return convertResultSetToDeveloper(rs, skills);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;

    }


    public Developer insert(Developer item){
        Connection connection = JdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
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
                JdbcUtils.printSQLException(e);
            }
            connection.commit();

            return getById(item.getId());

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;
    }

    public Developer update(Developer item){

        Connection connection = JdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, item.getFirstName());
            preparedStatement.setString(2, item.getLastName());
            preparedStatement.setLong(3, item.getSpecialty().getId());

            preparedStatement.setInt(4, item.getStatus().getId());
            preparedStatement.setLong(5, item.getId());

            preparedStatement.executeUpdate();

            //let`s write skills to bd
            skillRepository.setSkills2Developer(item.getSkills(), item);

            connection.commit();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }

        return getById(item.getId());

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

    private Developer convertResultSetToDeveloper(ResultSet rs, HashSet<Skill> skills) throws SQLException {

        long id = rs.getLong("id");
        String firstName = rs.getString("firstName");
        String secondName = rs.getString("lastName");
        long specialtyId = rs.getLong("specialtyId");
        int statusId = rs.getInt("statusId");
        Status status = Status.getStatusById(statusId);

        Specialty specialty = specialtyRepository.getById(specialtyId);
        return new Developer(id, firstName, secondName, skills,
                specialty, status );

    }


}
