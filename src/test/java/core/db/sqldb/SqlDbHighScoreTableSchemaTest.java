package core.db.sqldb;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlDbHighScoreTableSchemaTest {

    private static final String DB_URL = "jdbc:sqlite:game.db";

    @BeforeAll
    public static void setUp() throws SQLException {
        SqlDbFactory.highScoreTable();
    }

    @AfterAll
    public static void tearDown() {
        File file = new File("game.db");
        file.delete();
    }

    @Test
    void testHighScoreTableCreation() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            var resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='high_scores';");
            boolean tableExists = resultSet.next();
            assertThat(tableExists).isTrue();

            assertThat(hasColumn(statement, "id")).isTrue();
            assertThat(hasColumn(statement, "level_id")).isTrue();
            assertThat(hasColumn(statement, "username")).isTrue();
            assertThat(hasColumn(statement, "time")).isTrue();
        }
    }

    private boolean hasColumn(Statement statement, String columnName) throws SQLException {
        var resultSet = statement.executeQuery("PRAGMA table_info(high_scores);");
        while (resultSet.next()) {
            if (columnName.equals(resultSet.getString("name"))) {
                return true;
            }
        }
        return false;
    }
}
