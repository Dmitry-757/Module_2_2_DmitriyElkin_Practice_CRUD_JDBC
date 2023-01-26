package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import java.sql.SQLException;

public class StatusRepository {

    static void printSQLException(SQLException ex) {
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


//    public static long getStatusId(Status item){
//        long statusId = 0;
//        String selectStatement = "select * from status_tbl where status_value = "+item.getStatusValue();
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                statusId = rs.getLong("id");
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return statusId;
//    }

//    public static Status getById(long id){
//        int statusValue = -1;
//        String selectStatement = "select * from status_tbl where id = "+id;
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                statusValue = rs.getInt("statusValue");
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//
//        return Status.getStatusById(statusValue);
//    }
}
