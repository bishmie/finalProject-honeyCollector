package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;

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
    public static boolean update2(String ProductId, String ProductName, String SellingPrice,  String NetWeight, String Qty) throws SQLException {
        String sql = "UPDATE product SET productName =?, sellingPrice =?,netWeight =?, qty =? WHERE productId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,ProductName);
        pstm.setObject(2,SellingPrice);
        pstm.setObject(3,NetWeight);
        pstm.setObject(4,Qty);
        pstm.setObject(5,ProductId);

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
                   resultSet.getString(6)

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

}

