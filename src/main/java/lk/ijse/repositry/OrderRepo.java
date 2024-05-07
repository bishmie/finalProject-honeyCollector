package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getCustomerId());
        pstm.setDate(3, (Date) order.getOrderDate());

        return pstm.executeUpdate() > 0;
    }

    }

