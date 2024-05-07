package lk.ijse.repositry;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskRepo {


    public static boolean update2(String taskId, String name, String description, String date, String beekeeperId) throws SQLException {
        String sql = "UPDATE task SET taskName =?, description =?, dueDate =?, beekeeperId =? WHERE taskId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,name);
        pstm.setObject(2,description);
        pstm.setObject(3,date);
        pstm.setObject(4,beekeeperId);
        pstm.setObject(5,taskId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM task WHERE taskId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    }


