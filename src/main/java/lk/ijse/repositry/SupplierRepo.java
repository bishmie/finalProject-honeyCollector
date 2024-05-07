package lk.ijse.repositry;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierRepo {

    public static boolean update2(String supplierId, String name, String address, String contact, String email, String inventoryId) throws SQLException {
        String sql = "UPDATE supplier SET name =?, address =?,contact =?, email =?, inventoryId =? WHERE supplierId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,name);
        pstm.setObject(2,address);
        pstm.setObject(3,contact);
        pstm.setObject(4,email);
        pstm.setObject(5,inventoryId);
        pstm.setObject(6,supplierId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
