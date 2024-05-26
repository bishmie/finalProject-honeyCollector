package lk.ijse.repositry;


import lk.ijse.db.DbConnection;
import lk.ijse.model.BeeHive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeeHiveRepo {
    public static boolean save(BeeHive beeHive) throws SQLException {
        String sql = "INSERT INTO beehive VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, beeHive.getBeehiveId());
        pstm.setObject(2, beeHive.getType());
        pstm.setObject(3, beeHive.getLocation());
        pstm.setObject(4, beeHive.getPopulation());
        pstm.setObject(5, beeHive.getInspectionDate());
        pstm.setObject(6, beeHive.getInspectionResult());




        return pstm.executeUpdate() > 0;
    }
    public static BeeHive searchById(String id) throws SQLException {
        String sql = "SELECT * FROM beehive WHERE beehiveId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String beehiveId = resultSet.getString(1);
            String type = resultSet.getString(2);
            String location = resultSet.getString(3);
            String population = resultSet.getString(4);
            String inspectionDate = resultSet.getString(5);
            String inspectionResult = resultSet.getString(6);
            String queenId = resultSet.getString(7);

            BeeHive beeHive= new BeeHive(beehiveId, type, location, population, inspectionDate, inspectionResult );

            return beeHive;
        }

        return null;
    }
    public static boolean update(BeeHive beeHive) throws SQLException {
        String sql = "UPDATE beehive SET beehiveId = ?, type = ?, location = ?, population = ?, inspectionDate =?, inspectionResult =?, queenId =? WHERE beehiveId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, beeHive.getBeehiveId());
        pstm.setObject(2, beeHive.getType());
        pstm.setObject(3, beeHive.getLocation());
        pstm.setObject(4, beeHive.getPopulation());
        pstm.setObject(5, beeHive.getInspectionDate());
        pstm.setObject(6, beeHive.getInspectionResult());


        return pstm.executeUpdate() > 0;
    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM beehive WHERE beehiveId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static List<BeeHive> getAll() throws SQLException {
        String sql = "SELECT * FROM beehive";

        Connection con = DbConnection.getInstance().getConnection();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<BeeHive> beeHiveList = new ArrayList<>();

        while (resultSet.next()) {
            beeHiveList.add(new BeeHive(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                     resultSet.getString(6)

            ));
        }
        return beeHiveList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT beehiveId FROM beehive ";
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

    public static boolean update2(String id, String type, String location, String population, String inspectionDate, String inspectionResult) throws SQLException {
        String sql = "UPDATE beehive SET type =?, location =?,population =?, inspectionDate =?, inspectionResult =? WHERE beehiveId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,type);
        pstm.setObject(2,location);
        pstm.setObject(3,population);
        pstm.setObject(4,inspectionDate);
        pstm.setObject(5,inspectionResult);
        pstm.setObject(6,id);

        return pstm.executeUpdate() > 0;
    }


    }

