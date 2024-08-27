package core.db.app;

import core.db.domain.HighScore;

import java.sql.Time;
import java.util.List;

public interface HighScoreGateway {
    HighScore addHighScore(int levelId, String username, long time);
    List<HighScore> getBestScoresForLevel(int levelId, int limit);
}
