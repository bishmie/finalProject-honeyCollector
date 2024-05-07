package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.beeQueen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueenBeeRepo {

    public static boolean update2(String queenbeeId, String breedingHistory, String bodyFeatures, String healthStatus, String introducedDate, String variety) throws SQLException {
        String sql = "UPDATE beequeen SET breedingHistory =?, bodyFeatures =?, healthStatus =?, introducedDate =?, variety =? WHERE queenId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, breedingHistory);
        pstm.setObject(2, bodyFeatures);
        pstm.setObject(3, healthStatus);
        pstm.setObject(4, introducedDate);
        pstm.setObject(5, variety);
        pstm.setObject(6, queenbeeId);

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM beequeen WHERE queenId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<beeQueen> getAll() throws SQLException {
        String sql = "SELECT * FROM beequeen";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<beeQueen> beeQueenList = new ArrayList<>();

        while (resultSet.next()) {
            beeQueenList.add(new beeQueen(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return beeQueenList;
    }
}
