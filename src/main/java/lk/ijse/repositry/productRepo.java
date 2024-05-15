package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.OrderProduct;
import lk.ijse.model.Product;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productRepo {
    public static boolean save(Product product) throws SQLException {
        String sql = "INSERT INTO product VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, product.getProductId());
        pstm.setObject(2, product.getProductName());
        pstm.setObject(3, product.getSellingPrice());
        pstm.setObject(4, product.getNetWeight());
        pstm.setObject(5, product.getQty());


        return pstm.executeUpdate() > 0;
    }

    public static Product searchById(String id) throws SQLException {
        String sql = "SELECT * FROM product WHERE productId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String ProductId = resultSet.getString(1);
            String ProductName = resultSet.getString(2);
            String SellingPrice = resultSet.getString(3);
            String NetWeight = resultSet.getString(4);
            String Qty = resultSet.getString(5);

            Product product = new Product(ProductId, ProductName, SellingPrice, NetWeight, Qty);

            return product;
        }

        return null;
    }

    public static boolean update2(String ProductId, String ProductName, String SellingPrice, String NetWeight, String Qty) throws SQLException {
        String sql = "UPDATE product SET productName =?, sellingPrice =?,netWeight =?, qty =? WHERE productId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, ProductName);
        pstm.setObject(2, SellingPrice);
        pstm.setObject(3, NetWeight);
        pstm.setObject(4, Qty);
        pstm.setObject(5, ProductId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM product WHERE productId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM product";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            productList.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)


            ));
        }
        return productList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT productId FROM product";
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

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT productId FROM product";
        ResultSet resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static boolean update(List<OrderProduct> odList) throws SQLException {
        for (OrderProduct od : odList) {
            boolean isUpdateQty = updateQty(od.getProductId(), od.getQty());
            if (!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String productId, int qty) throws SQLException {
        String sql = "UPDATE product SET qty = qty - ? WHERE productId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, productId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Product> getAllProductsFromDatabase() {
        // Implement this method to fetch products from your database
        // For example:
        List<Product> productList = new ArrayList<>();
        // Fetch products from the database and add them to productList
        return productList;
    }

}


