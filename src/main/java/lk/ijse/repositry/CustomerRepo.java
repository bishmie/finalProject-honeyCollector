package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, customer.getCustomerId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getContact());
        pstm.setObject(5, customer.getEmail());


        return pstm.executeUpdate() > 0;
    }
    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);

            Customer customer = new Customer(CustomerId, name, address, contact, email );

            return customer;
        }

        return null;
    }
    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET customerId = ?, name = ?, address = ?, contact = ?, email =? WHERE customerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getContact());
        pstm.setObject(5, customer.getEmail());

        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE customerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            cusList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return cusList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT customerId FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;

    }

    public static boolean update2(String customerId, String Name, String Address,  String Contact, String Email) throws SQLException {
        String sql = "UPDATE customer SET name =?, address =?,contact =?, email =? WHERE customerId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,Name);
        pstm.setObject(2,Address);
        pstm.setObject(3,Contact);
        pstm.setObject(4,Email);
        pstm.setObject(5,customerId);

        return pstm.executeUpdate() > 0;
    }


    public static List<String> getNames() {
        String sql = "SELECT name FROM customer";
        PreparedStatement pstm = null;
        try {
            pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = null;
        try {
            resultSet = pstm.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String id = null;
            try {
                id = resultSet.getString(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            idList.add(id);
        }
        return idList;

    }
}
