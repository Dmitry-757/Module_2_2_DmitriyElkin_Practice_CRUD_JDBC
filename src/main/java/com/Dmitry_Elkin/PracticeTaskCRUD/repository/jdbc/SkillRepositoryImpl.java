package com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SkillRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.utils.JdbcUtils;


import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    private static final String SELECT_ALL = "select * from skills_tbl";

    private static final String UPDATE_SQL = "update skills_tbl set name = ?, statusId = ? where id = ?;";



    @Override
    public List<Skill> getAll(Status status) {
        String sql;
        if (status == null){
            sql = SELECT_ALL;
        } else {
            sql = SELECT_ALL + " where statusId = " + status.getId();
        }

        List<Skill> itemList = new LinkedList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itemList.add(convertResultSetToSkill(rs));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return itemList;
    }

    @Override
    public List<Skill> getAll() {
//        List<Skill> itemList = new LinkedList<>();
//        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SELECT_ALL)) {
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                itemList.add(convertResultSetToSkill(rs));
//            }
//        } catch (SQLException e) {
//            JdbcUtils.printSQLException(e);
//        }
        return getAll(null);
    }

    @Override
    public Skill getById(Long itemId) {
        String sql = SELECT_ALL + " where id = " + itemId;
        List<Skill> itemList = new LinkedList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                itemList.add(convertResultSetToSkill(rs));
            }
            if (itemList.size()>0){
                return itemList.get(0);
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return null;
    }

    public HashSet<Skill> getSkillsByDeveloper(long developerId){
        HashSet<Skill> itemSet = new HashSet<>();
        String sql = " select * from developer2skills_tbl where developerId = " + developerId;
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            LinkedList<Long> idOfSkills = new LinkedList<>();

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long skillId = rs.getLong("skillId");
                idOfSkills.add(skillId);
            }

            for (Long id:idOfSkills) {
                itemSet.add(getById(id));
            }

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return itemSet;
    }

    public void setSkills2Developer(HashSet<Skill> skills, Developer item){
        HashSet<Skill> currentSkills = getSkillsByDeveloper(item.getId());
        if (currentSkills.equals(skills)){
            return;
        }

        //let`s write skills to bd
        String sqlDelete = "delete from developer2skills_tbl where developerId = "+ item.getId();
        String sqlInsert = "INSERT developer2skills_tbl(developerId, skillId) VALUES (?, ?)";
        try (PreparedStatement ps4Delete = JdbcUtils.getPreparedStatement(sqlDelete);
                PreparedStatement ps4Insert = JdbcUtils.getPreparedStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            //зачистим текущие записи скилов
            ps4Delete.executeUpdate();

            //запишем новые
            for (Skill skill : skills) {
                ps4Insert.setLong(1, item.getId());
                if (skill.getId() == 0) {
                    insert(skill);
                }
                ps4Insert.setLong(2, skill.getId());

                ps4Insert.addBatch();
            }
            int[] rows = ps4Insert.executeBatch();
            System.out.println("to developer2skills_tbl where added " + (rows.length) +" record(s)");
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
    }


    @Override
    public Skill insert(Skill item) {
        String sql = "INSERT developer2skills_tbl(developerId, skillId) VALUES (?, ?)";
        try (PreparedStatement statement = JdbcUtils.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getStatus().getId());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return getById(item.getId());
    }

    @Override
    public Skill update(Skill item) {
        try (PreparedStatement statement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getStatus().getId());
            statement.setLong(3, item.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            JdbcUtils.printSQLException(e);
        }
        return getById(item.getId());
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

    private Skill convertResultSetToSkill(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        int statusId = rs.getInt("statusId");
        return new Skill(id, name, statusId);
    }


}
