package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
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

    private static final String SELECT_ALL = "select * from skills_tbl";

    private static final String UPDATE_SQL = "update skills_tbl set name = ?, statusId = ? where id = ?;";


    @Override
    public List<Skill> getAll(Status status) {
        String selectStatement;
        if (status == null){
            selectStatement = SELECT_ALL;
        } else {
            selectStatement = SELECT_ALL + " where statusId = " + status.getId();
        }

        List<Skill> itemList = new LinkedList<>();
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Skill(id, name, statusId));
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
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
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int statusId = rs.getInt("statusId");
                itemList.add(new Skill(id, name, statusId));
            }
            if (itemList.size()>0){
                return itemList.get(0);
            }
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
        }
        return null;
    }

    public HashSet<Skill> getSkillsFromLinkTable(long developerId){
        HashSet<Skill> itemSet = new HashSet<>();
        String selectStatement = " select * from developer2skills_tbl where developerId = " + developerId;
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {

            LinkedList<Long> ids = new LinkedList<>();

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
//                long id = rs.getLong("id");
                long skillId = rs.getLong("skillId");
                ids.add(skillId);
            }

            for (Long id:ids) {
                itemSet.add(getById(id));
            }

        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
        }
        return itemSet;
    }

    public void setSkills2Developer(HashSet<Skill> skills, Developer item){
        Connection connection = DBConnection.getConnection();
        HashSet<Skill> currentSkills = getSkillsFromLinkTable(item.getId());
        if (currentSkills.equals(skills)){
            return;
        }

        //let`s write skills to bd
        try( Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement("INSERT developer2skills_tbl(developerId, skillId) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            //зачистим текущие записи скилов
            statement.executeUpdate("delete from developer2skills_tbl where developerId = "+ item.getId());
            //запишем новые
            for (Skill skill : skills) {
                ps.setLong(1, item.getId());
                if (skill.getId() == 0) {
                    insert(skill);
                }
                ps.setLong(2, skill.getId());

                ps.addBatch();
            }
            int[] rows = ps.executeBatch();
            System.out.println("to developer2skills_tbl where added " + (rows.length) +" record(s)");
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
        }
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

    public void update(Skill item) {
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getStatus().getId());
            statement.setLong(3, item.getId());
            statement.executeUpdate();

//            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            StatusRepository.printSQLException(e);
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



}
