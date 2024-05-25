package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.BeeHive;
import lk.ijse.model.beeQueen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueenBeeRepo {

    public static boolean update2(String queenbeeId, String breedingHistory, String bodyFeatures, String healthStatus, String introducedDate, String beehiveId, String variety) throws SQLException {
        String sql = "UPDATE beequeen SET breedingHistory =?, bodyFeatures =?, healthStatus =?, introducedDate =?, beehiveId =? , variety =? WHERE queenId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, breedingHistory);
        pstm.setObject(2, bodyFeatures);
        pstm.setObject(3, healthStatus);
        pstm.setObject(4, introducedDate);
        pstm.setObject(5, beehiveId);
        pstm.setObject(6, variety);
        pstm.setObject(7, queenbeeId);

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
        String sql = "SELECT * FROM beequeen WHERE beehiveId IS NOT NULL ";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<beeQueen> beeQueenList = new ArrayList<>();

        while (resultSet.next()) {
            beeQueenList.add(new beeQueen(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
                    )

            );
        }
        return beeQueenList;
    }




    public static List<beeQueen> getAllAssignedBees() throws SQLException {
        String sql = "SELECT beequeen.queenId, beequeen.FROM beequeen WHERE beehiveId IS NOT NULL ";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<beeQueen> beeQueenList = new ArrayList<>();

        while (resultSet.next()) {
            beeQueenList.add(new beeQueen(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    )

            );
        }
        return beeQueenList;
    }
}

