package core.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;

import java.util.List;

public class HighScoreController {
    private final HighScoreInteractor highScoreInteractor;

    public HighScoreController(HighScoreInteractor highScoreInteractor) {
        this.highScoreInteractor = highScoreInteractor;
    }

    public String addHighScore(int levelId, String username, long time) {
        HighScore newScore = highScoreInteractor.addHighScore(levelId, username, time);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("id", newScore.getScoreId());
        jsonResponse.addProperty("levelId", newScore.getLevelId());
        jsonResponse.addProperty("username", newScore.getUsername());
        jsonResponse.addProperty("time", newScore.getTime());
        return jsonResponse.toString();
    }

    public String getBestScores(int levelId, int limit) {
        List<HighScore> highScores = highScoreInteractor.getBestScoresForLevel(levelId, limit);
        JsonArray jsonResponse = new JsonArray();
        for (HighScore score : highScores) {
            JsonObject jsonScore = new JsonObject();
            jsonScore.addProperty("id", score.getScoreId());
            jsonScore.addProperty("levelId", score.getLevelId());
            jsonScore.addProperty("username", score.getUsername());
            jsonScore.addProperty("time", score.getTime());
            jsonResponse.add(jsonScore);
        }
        return jsonResponse.toString();
    }

    public String getBestScoreForUser(int levelId, String username) {
        HighScore bestScore = highScoreInteractor.getBestScoreForUserAndLevel(levelId, username);
        JsonObject jsonResponse = new JsonObject();
        if (bestScore != null) {
            jsonResponse.addProperty("id", bestScore.getScoreId());
            jsonResponse.addProperty("levelId", bestScore.getLevelId());
            jsonResponse.addProperty("username", bestScore.getUsername());
            jsonResponse.addProperty("time", bestScore.getTime());
        }
        return jsonResponse.toString();
    }
}