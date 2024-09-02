package core.db.database;

import core.db.app.HighScoreGateway;
import core.db.domain.HighScore;

import java.util.List;

public class DbHighScoreGateway implements HighScoreGateway {
    private final DbHighScoreTable table;

    public DbHighScoreGateway(DbHighScoreTable table) {
        this.table = table;
    }

    public HighScore addHighScore(int levelId, String username, long time) {
        int id = table.insertHighScore(levelId, username, time);
        return new HighScore(id, levelId, username, time);
    }

    public List<HighScore> getBestScoresForLevel(int levelId, int limit) {
        return table.selectBestScoresForLevel(levelId, limit);
    }

    public HighScore getBestScoreForUserAndLevel(int levelId, String username) {
        return table.selectBestScoreForUserAndLevel(levelId, username);
    }
}

