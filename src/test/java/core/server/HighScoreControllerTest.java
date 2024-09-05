package core.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HighScoreControllerTest {

    private HighScoreInteractor highScoreInteractor;
    private HighScorePresenter presenter;
    private HighScoreController controller;

    @BeforeEach
    void setUp() {
        highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        presenter = new HighScorePresenter();
        controller = new HighScoreController(highScoreInteractor, presenter);
    }

    @Test
    void testAddHighScore() {
        HighScore newScore = new HighScore(1, 10, "player1", 1234L);
        when(highScoreInteractor.addHighScore(10, "player1", 1234L)).thenReturn(newScore);

        String result = controller.addHighScore(10, "player1", 1234L);

        JsonObject expectedJson = new JsonObject();
        expectedJson.addProperty("id", 1);
        expectedJson.addProperty("levelId", 10);
        expectedJson.addProperty("username", "player1");
        expectedJson.addProperty("time", 1234L);

        assertEquals(expectedJson.toString(), result);
    }

    @Test
    void testGetBestScores() {
        List<HighScore> scores = Arrays.asList(
                new HighScore(1, 10, "player1", 1234L),
                new HighScore(2, 10, "player2", 5678L)
        );
        when(highScoreInteractor.getBestScoresForLevel(10, 10)).thenReturn(scores);

        String result = controller.getBestScores(10, 10);

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

        assertEquals(jsonArray.toString(), result);
    }

    @Test
    void testGetBestScoreForUser() {
        HighScore bestScore = new HighScore(1, 10, "player1", 1234L);
        when(highScoreInteractor.getBestScoreForUserAndLevel(10, "player1")).thenReturn(bestScore);

        String result = controller.getBestScoreForUser(10, "player1");

        JsonObject expectedJson = new JsonObject();
        expectedJson.addProperty("id", 1);
        expectedJson.addProperty("levelId", 10);
        expectedJson.addProperty("username", "player1");
        expectedJson.addProperty("time", 1234L);

        assertEquals(expectedJson.toString(), result);
    }
}
