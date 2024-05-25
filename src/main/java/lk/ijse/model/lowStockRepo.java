package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class lowStockRepo {

    public List<String> getLowStockProducts(int threshold) throws SQLException {
        List<String> lowStockProducts = new ArrayList<>();
        String query = "SELECT product_id FROM Product WHERE qty < ?";

        Connection connection = DbConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, threshold);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                lowStockProducts.add(resultSet.getString("productId"));
            }
        }
        return lowStockProducts;
    }
}
