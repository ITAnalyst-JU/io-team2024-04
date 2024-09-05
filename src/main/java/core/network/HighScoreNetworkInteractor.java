package core.network;

import core.db.domain.HighScore;

import java.util.List;

public class HighScoreNetworkInteractor {
    private final HighScoreClient highScoreClient;

    public HighScoreNetworkInteractor(HighScoreClient highScoreClient) {
        this.highScoreClient = highScoreClient;
    }

    public void addHighScore(int levelId, String username, long time, Callback<Void> callback) {
        highScoreClient.addHighScore(levelId, username, time, new HighScoreClient.Callback<>() {
            @Override
            public void onSuccess(Void result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    public void getBestScores(int levelId, int limit, Callback<List<HighScore>> callback) {
        highScoreClient.getTopScores(levelId, limit, new HighScoreClient.Callback<>() {
            @Override
            public void onSuccess(List<HighScore> highScores) {
                callback.onSuccess(highScores);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    public void getBestScoreForUser(int levelId, String username, Callback<HighScore> callback) {
        highScoreClient.getBestScoreForUser(levelId, username, new HighScoreClient.Callback<>() {
            @Override
            public void onSuccess(HighScore highScore) {
                callback.onSuccess(highScore);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
}
