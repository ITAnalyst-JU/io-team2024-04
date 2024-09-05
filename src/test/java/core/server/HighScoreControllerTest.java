package core.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HighScoreControllerTest {
    private HighScoreInteractor mockInteractor;
    private HighScoreController controller;

    @BeforeEach
    public void setUp() {
        mockInteractor = Mockito.mock(HighScoreInteractor.class);
        controller = new HighScoreController(mockInteractor);
    }

    @Test
    public void testAddHighScore() {
        HighScore mockScore = new HighScore(1, 1, "user1", 123L);
        when(mockInteractor.addHighScore(1, "user1", 123L)).thenReturn(mockScore);

        String response = controller.addHighScore(1, "user1", 123L);
        JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);

        assertEquals(1, jsonResponse.get("id").getAsInt());
        assertEquals(1, jsonResponse.get("levelId").getAsInt());
        assertEquals("user1", jsonResponse.get("username").getAsString());
        assertEquals(123L, jsonResponse.get("time").getAsLong());
    }

    @Test
    public void testGetBestScores() {
        List<HighScore> mockScores = Arrays.asList(
                new HighScore(1, 1, "user1", 123L),
                new HighScore(2, 1, "user2", 150L)
        );
        when(mockInteractor.getBestScoresForLevel(1, 10)).thenReturn(mockScores);

        String response = controller.getBestScores(1, 10);
        JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);

        assertEquals(2, jsonArray.size());
        JsonObject firstScore = jsonArray.get(0).getAsJsonObject();
        assertEquals(1, firstScore.get("id").getAsInt());
        assertEquals("user1", firstScore.get("username").getAsString());
    }

    @Test
    public void testGetBestScoreForUser() {
        HighScore mockScore = new HighScore(1, 1, "user1", 123L);
        when(mockInteractor.getBestScoreForUserAndLevel(1, "user1")).thenReturn(mockScore);

        String response = controller.getBestScoreForUser(1, "user1");
        JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);

        assertEquals(1, jsonResponse.get("id").getAsInt());
        assertEquals("user1", jsonResponse.get("username").getAsString());
    }
}