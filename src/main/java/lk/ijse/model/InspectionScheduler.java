package lk.ijse.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InspectionScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final String jdbcUrl = "jdbc:mysql://localhost:3306/honeycollector";
    private final String jdbcUser = "root";
    private final String jdbcPassword = "bishmi123#";

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkAndSendEmails, 0, 1, TimeUnit.DAYS);
    }

    private void checkAndSendEmails() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
            String query = "SELECT b.beekeeperId, bm.beehiveId, bm.inspectionDate, bk.email FROM beehive bm JOIN beehiveManage b ON bm.beehiveId = b.beehiveId JOIN beekeeper bk ON bk.beekeeperId = b.beekeeperId WHERE bm.inspectionDate = CURDATE()";
            try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String beekeeperId = String.valueOf(rs.getInt("beekeeperId"));
                    String beehiveId = String.valueOf(rs.getInt("beehiveId"));
                    String email = rs.getString("email");
                    String inspectionDate = rs.getString("inspectionDate");

                    String subject = "Hive Inspection Reminder";
                    String body = "Dear Beekeeper,\n\nThis is a reminder that the hive with ID " + beehiveId + " is scheduled for inspection today (" + inspectionDate + ").\n\nBest regards,\nBeehive Management System";

                    EmailSender.sendEmail(email, subject, body);

                    // Update the next inspection date
                    updateNextInspectionDate(conn, Integer.parseInt(beehiveId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateNextInspectionDate(Connection conn, int beehiveId) throws SQLException {
        String updateQuery = "UPDATE beehive SET inspectionDate = DATE_ADD(inspectionDate, INTERVAL 15 DAY) WHERE beehiveId = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            updateStmt.setInt(1, beehiveId);
            updateStmt.executeUpdate();
        }
    }

    public void stop() {
        scheduler.shutdown();
    }
    public static void main(String[] args) {
        InspectionScheduler inspectionScheduler = new InspectionScheduler();
        inspectionScheduler.start();
    }


}
