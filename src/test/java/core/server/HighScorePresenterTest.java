package core.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighScorePresenterTest {

    private final HighScorePresenter presenter = new HighScorePresenter();

    @Test
    void testFormatNewScore() {
        HighScore highScore = new HighScore(1, 10, "player1", 1234L);
        String jsonResult = presenter.formatNewScore(highScore);

        JsonObject expectedJson = new JsonObject();
        expectedJson.addProperty("id", 1);
        expectedJson.addProperty("levelId", 10);
        expectedJson.addProperty("username", "player1");
        expectedJson.addProperty("time", 1234L);

        assertEquals(expectedJson.toString(), jsonResult);
    }

    @Test
    void testFormatBestScores() {
        List<HighScore> scores = Arrays.asList(
                new HighScore(1, 10, "player1", 1234L),
                new HighScore(2, 10, "player2", 5678L)
        );
        String jsonResult = presenter.formatBestScores(scores);

        JsonArray jsonArray = new JsonArray();

        JsonObject score1 = new JsonObject();
        score1.addProperty("id", 1);
        score1.addProperty("levelId", 10);
        score1.addProperty("username", "player1");
        score1.addProperty("time", 1234L);
        jsonArray.add(score1);

        JsonObject score2 = new JsonObject();
        score2.addProperty("id", 2);
        score2.addProperty("levelId", 10);
        score2.addProperty("username", "player2");
        score2.addProperty("time", 5678L);
        jsonArray.add(score2);

        assertEquals(jsonArray.toString(), jsonResult);
    }

    @Test
    void testFormatBestScoreForUser() {
        HighScore highScore = new HighScore(1, 10, "player1", 1234L);
        String jsonResult = presenter.formatBestScoreForUser(highScore);

        JsonObject expectedJson = new JsonObject();
        expectedJson.addProperty("id", 1);
        expectedJson.addProperty("levelId", 10);
        expectedJson.addProperty("username", "player1");
        expectedJson.addProperty("time", 1234L);

        assertEquals(expectedJson.toString(), jsonResult);
    }
}
