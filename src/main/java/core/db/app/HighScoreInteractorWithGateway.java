package core.db.app;

import core.db.domain.HighScore;

import java.sql.Time;
import java.util.List;

public class HighScoreInteractorWithGateway implements HighScoreInteractor {
    private final HighScoreGateway gateway;

    public HighScoreInteractorWithGateway(HighScoreGateway gateway) {
        this.gateway = gateway;
    }

    public HighScore addHighScore(int levelId, String username, long time) {
        return gateway.addHighScore(levelId, username, time);
    }

    public List<HighScore> getBestScoresForLevel(int levelId, int limit) {
        return gateway.getBestScoresForLevel(levelId, limit);
    }
}
