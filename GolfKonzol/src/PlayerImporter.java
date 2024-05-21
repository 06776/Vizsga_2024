import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerImporter {
    private final Connection connection;

    public PlayerImporter(DatabaseManager dbManager) {
        this.connection = dbManager.getConnection();
    }

    public int importPlayers(String filePath) throws IOException, SQLException {
        String insertSQL = "INSERT INTO Player (name) VALUES (?)";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    ps.setString(1, line.trim());
                    ps.executeUpdate();
                    count++;
                }
            }
            return count;
        }
    }
}
