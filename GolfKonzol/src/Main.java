import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java Main <GolfJatekosok.txt path> <GolfEredmenyek.csv path>");
            return;
        }

        String playerFilePath = args[0];
        String resultFilePath = args[1];

        DatabaseManager dbManager = new DatabaseManager();

        try {
            dbManager.connect();

            PlayerImporter playerImporter = new PlayerImporter(dbManager);
            int playersImported = playerImporter.importPlayers(playerFilePath);
            System.out.println(playersImported + " players imported successfully.");

            ResultImporter resultImporter = new ResultImporter(dbManager);
            int resultsImported = resultImporter.importResults(resultFilePath);
            System.out.println(resultsImported + " results imported successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.disconnect();
        }
    }
}
