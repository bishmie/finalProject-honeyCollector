package lk.ijse.repositry;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Harvest;
import lk.ijse.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HarvestRepo {

    public static boolean save(Harvest harvest) throws SQLException {

        String sql = "INSERT INTO harvest VALUES(?, ?, ?, ?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, harvest.getHarvestId());
        pstm.setObject(2, harvest.getProductionDate());
        pstm.setObject(3, harvest.getAmountOfLiters());
        pstm.setObject(4, harvest.getQualityNotes());
        pstm.setObject(5, harvest.getBeehiveId());
        pstm.setObject(6, harvest.getCollectorId());
        pstm.setObject(7, harvest.getHarvestType());
        pstm.setObject(8, harvest.getGrade());

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(Harvest harvest) throws SQLException {
        String sql = "UPDATE harvest SET productionDate = ?, amountOfLitres = ?,  qualityNotes = ?,  beehiveId =?, collectorId =?, harvestType =?, grade =? WHERE harvestId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, harvest.getProductionDate());
        pstm.setObject(2, harvest.getAmountOfLiters());
        pstm.setObject(3, harvest.getQualityNotes());
        pstm.setObject(4, harvest.getBeehiveId());
        pstm.setObject(5, harvest.getCollectorId());
        pstm.setObject(6, harvest.getHarvestType());
        pstm.setObject(7, harvest.getGrade());
        pstm.setObject(8, harvest.getHarvestId());

        return pstm.executeUpdate() > 0;
    }

    public static Harvest searchById(String id) throws SQLException {
        String sql = "SELECT * FROM harvest WHERE harvestId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String harvestId = resultSet.getString(1);
            String productionDate = resultSet.getString(2);
            String amountOfLiters = resultSet.getString(3);
            String qualityNotes = resultSet.getString(4);
            String beehiveId = resultSet.getString(5);
            String collectorId = resultSet.getString(6);
            String harvestType = resultSet.getString(7);
            String grade = resultSet.getString(8);


            Harvest harvest = new Harvest(harvestId,productionDate,amountOfLiters,qualityNotes,beehiveId,collectorId,harvestType,grade);

            return harvest;
        }

        return null;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM harvest WHERE harvestId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Harvest> getAll() throws SQLException {
        String sql = "SELECT * FROM harvest";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<Harvest> harvestList = new ArrayList<>();

        while (resultSet.next()) {
            harvestList.add(new Harvest(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)


            ));
        }
        return harvestList;
    }
}
