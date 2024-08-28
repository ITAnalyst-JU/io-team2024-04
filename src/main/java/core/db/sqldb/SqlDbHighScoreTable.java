package core.db.sqldb;

import core.db.database.DbHighScoreTable;
import core.db.domain.HighScore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class SqlDbHighScoreTable implements DbHighScoreTable {
    private final SqlDbEngine engine;

    SqlDbHighScoreTable(SqlDbEngine engine) { this.engine = engine; }

    void createTableIfNotExists() {
        var statement = """
            CREATE TABLE IF NOT EXISTS high_scores (id INTEGER PRIMARY KEY, \
            level_id INT NOT NULL, username TEXT NOT NULL, time BIGINT NOT NULL)""";
        engine.execute(statement, PreparedStatement::executeUpdate);
    }

    public void readHighScores(DbHighScoreTable.HighScoreListBuilder builder) {
        var statement = "SELECT * FROM high_scores ORDER BY id";
        engine.execute(statement, sql -> {
            var rs = sql.executeQuery();
            while (rs.next()) builder.add(
                    rs.getInt("id"),
                    rs.getInt("level_id"),
                    rs.getString("username"),
                    rs.getLong("time"));
        });
    }

    public int insertHighScore(int levelId, String username, long time) {
        var result = new AtomicInteger();
        var statement = "INSERT INTO high_scores (level_id, username, time) VALUES (?, ?, ?) RETURNING id";
        engine.execute(statement, sql -> {
            sql.setInt(1, levelId);
            sql.setString(2, username);
            sql.setLong(3, time);
            ResultSet rs = sql.executeQuery();
            rs.next();
            result.set(rs.getInt("id"));
        });
        return result.get();
    }

    public List<HighScore> selectBestScoresForLevel(int levelId, int limit) {
        List<HighScore> highScores = new ArrayList<>();
        var statement = "SELECT * FROM high_scores WHERE level_id = ? ORDER BY time ASC LIMIT ?";
        engine.execute(statement, sql -> {
            sql.setInt(1, levelId);
            sql.setInt(2, limit);
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                highScores.add(new HighScore(
                        rs.getInt("id"),
                        rs.getInt("level_id"),
                        rs.getString("username"),
                        rs.getLong("time")));
            }
        });
        return highScores;
    }
}

