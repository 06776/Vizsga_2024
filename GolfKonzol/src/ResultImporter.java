import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultImporter {
    private final Connection connection;

    public ResultImporter(DatabaseManager dbManager) {
        this.connection = dbManager.getConnection();
    }

    public int importResults(String filePath) throws IOException, SQLException {
        String insertSQL = "INSERT INTO Result (golfTournament, playerId, round1, round2, round3, round4, totalRounds) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] values = line.split(",");
                    ps.setString(1, values[0].trim());
                    ps.setInt(2, Integer.parseInt(values[1].trim()));
                    ps.setInt(3, Integer.parseInt(values[2].trim()));
                    ps.setInt(4, Integer.parseInt(values[3].trim()));
                    ps.setInt(5, Integer.parseInt(values[4].trim()));
                    ps.setInt(6, Integer.parseInt(values[5].trim()));
                    ps.setInt(7, Integer.parseInt(values[6].trim()));
                    ps.executeUpdate();
                    count++;
                }
            }
            return count;
        }
    }
}
