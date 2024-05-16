package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Collector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class collectorRepo {
    public static boolean save(Collector collector) throws SQLException {
        String sql = "INSERT INTO honeycollector VALUES(?, ?, ?, ?,?,?)";

        Connection connection;
        connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, collector.getCollectorId());
        pstm.setObject(2, collector.getName());
        pstm.setObject(3, collector.getAddress());
        pstm.setObject(4, collector.getContact());
        pstm.setObject(5, collector.getEmail());
        pstm.setObject(6, collector.getSalary());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Collector collector) throws SQLException {
        String sql = "UPDATE honeycollector SET name = ?, address = ?, contact = ?, email = ?, salary = ? WHERE collectorId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, collector.getName());
        pstm.setObject(2, collector.getAddress());
        pstm.setObject(3, collector.getContact());
        pstm.setObject(4, collector.getEmail());
        pstm.setObject(5, collector.getSalary());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM honeycollector WHERE collectorId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static Collector searchById(String id) throws SQLException {
        String sql = "SELECT * FROM honeycollector WHERE collectorId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String collectorId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);
            double salary = resultSet.getDouble(6);

            Collector collector = new Collector(collectorId, name, address, contact, email, salary);

            return collector;
        }
        return null;
    }

    public static List<Collector> getAll() throws SQLException {
        String sql = "SELECT * FROM honeycollector";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Collector> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);
            double salary = resultSet.getDouble(6);

            Collector collector = new Collector(id, name, address, contact, email, salary);
            cusList.add(collector);
        }
        return cusList;
    }
}
