package core.db.database;

import core.db.domain.HighScore;

import java.sql.Time;
import java.util.List;

public interface DbHighScoreTable {
    interface HighScoreListBuilder { void add(int id, int levelId, String username, long time); }

    void readHighScores(HighScoreListBuilder builder);
    int insertHighScore(int levelId, String username, long time);
    List<HighScore> selectBestScoresForLevel(int levelId, int limit);

}
