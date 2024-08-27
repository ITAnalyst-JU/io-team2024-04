package core.db.database;

import core.db.app.HighScoreGateway;
import core.db.domain.HighScore;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHighScoreGateway implements HighScoreGateway {
    private final DbHighScoreTable table;
    private final Map<HighScore, Integer> ids = new HashMap<>();

    public DbHighScoreGateway(DbHighScoreTable table) {
        this.table = table;
    }

    private HighScore createHighScore(int id, int levelId, String username, long time) {
        HighScore highScore = new HighScore(id, levelId, username, time);
        ids.put(highScore, id);
        return highScore;
    }

    public List<HighScore> loadHighScores() {
        List<HighScore> list = new ArrayList<>();
        table.readHighScores((id, levelId, username, time) -> list.add(createHighScore(id, levelId, username, time)));
        return list;
    }

    public HighScore addHighScore(int levelId, String username, long time) {
        int id = table.insertHighScore(levelId, username, time);
        return createHighScore(id, levelId, username, time);
    }

    public List<HighScore> getBestScoresForLevel(int levelId, int limit) {
        return table.selectBestScoresForLevel(levelId, limit);
    }
}

