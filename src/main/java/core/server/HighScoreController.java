package core.server;

import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;

import java.util.List;

public class HighScoreController {
    private final HighScoreInteractor highScoreInteractor;
    private final HighScorePresenter presenter;

    public HighScoreController(HighScoreInteractor highScoreInteractor, HighScorePresenter presenter) {
        this.highScoreInteractor = highScoreInteractor;
        this.presenter = presenter;
    }

    public String addHighScore(int levelId, String username, long time) {
        HighScore newScore = highScoreInteractor.addHighScore(levelId, username, time);
        return presenter.formatNewScore(newScore);
    }

    public String getBestScores(int levelId, int limit) {
        List<HighScore> highScores = highScoreInteractor.getBestScoresForLevel(levelId, limit);
        return presenter.formatBestScores(highScores);
    }

    public String getBestScoreForUser(int levelId, String username) {
        HighScore bestScore = highScoreInteractor.getBestScoreForUserAndLevel(levelId, username);
        return presenter.formatBestScoreForUser(bestScore);
    }
}
