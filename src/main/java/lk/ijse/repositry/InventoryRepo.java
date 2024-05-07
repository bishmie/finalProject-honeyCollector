package lk.ijse.repositry;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryRepo {
    public static boolean update2(String inventoryId, String type, String description, String qty, String unitPrice, String beehiveId) throws SQLException {
        String sql = "UPDATE inventory SET type =?, description =?,qty =?, unitPrice =?, beehiveId =? WHERE inventoryId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,type);
        pstm.setObject(2,description);
        pstm.setObject(3,qty);
        pstm.setObject(4,unitPrice);
        pstm.setObject(5,beehiveId);
        pstm.setObject(6,inventoryId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM inventory WHERE inventoryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}

