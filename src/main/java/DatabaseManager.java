import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:/home/abstrato/Documents/Personal/Repositories/java-project/FlockFinder/WebContent/WEB-INF/data/weights.db";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cattle_weights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cattle_id TEXT NOT NULL," +
                "weight REAL NOT NULL);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("Executing SQL statement: " + sql);
            stmt.execute(sql);
            System.out.println("Table 'cattle_weights' created successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertData(String cattleId, double weight) {
        String sql = "INSERT INTO cattle_weights (cattle_id, weight) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cattleId);
            pstmt.setDouble(2, weight);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    public static List<CattleWeight> getData() {
        String sql = "SELECT * FROM cattle_weights";
        List<CattleWeight> data = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String cattleId = rs.getString("cattle_id");
                double weight = rs.getDouble("weight");
                data.add(new CattleWeight(id, cattleId, weight));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}
