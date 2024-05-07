package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.beekeeper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeeKeeperManageRepo {
    public static boolean save(beekeeper bk) throws SQLException {
        String sql = "INSERT INTO beekeeper VALUES(?, ?, ?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, bk.getBeekeeperId());
        pstm.setObject(2, bk.getName());
        pstm.setObject(3, bk.getAddress());
        pstm.setObject(4, bk.getContact());
        pstm.setObject(5, bk.getEmail());
        pstm.setObject(6, bk.getSalary());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update2(String beekeeperId, String Name, String Address, String Contact, String Email, String Salary) throws SQLException {
        String sql = "UPDATE beekeeper SET name =?, address =?,contact =?, email =?, salary =? WHERE beekeeperId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, Name);
        pstm.setObject(2, Address);
        pstm.setObject(3, Contact);
        pstm.setObject(4, Email);
        pstm.setObject(5, Salary);
        pstm.setObject(6, beekeeperId);


        return pstm.executeUpdate() > 0;
    }

    public static beekeeper searchById(String id) throws SQLException {
        String sql = "SELECT * FROM beekeeper WHERE beekeeperId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String beekeeperId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);
            String salary = resultSet.getString(6);

            beekeeper bk = new beekeeper(beekeeperId, name, address, contact, email,salary);

            return bk;
        }

        return null;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM beekeeper WHERE beekeeperId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
