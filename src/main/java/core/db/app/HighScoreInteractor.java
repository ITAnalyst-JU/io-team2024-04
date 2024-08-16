package core.db.app;

import core.db.domain.HighScore;

import java.util.List;
import java.sql.Time;

public interface HighScoreInteractor {
    HighScore addHighScore(int levelId, String username, Time time);
    List<HighScore> getBestScoresForLevel(int levelId, int limit);
}
