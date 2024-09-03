package core.db.sqldb;

import core.db.domain.HighScore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SqlDbHighScoreTableIntegrationTest {

    private SqlDbHighScoreTable highScoreTable;
    private File dbFile = new File("test.db");

    @BeforeEach
    public void setUp() {
        SqlDbEngine engine = new SqlDbEngine("jdbc:sqlite:test.db");
        highScoreTable = new SqlDbHighScoreTable(engine);
        highScoreTable.createTableIfNotExists();
    }

    @AfterEach
    public void tearDown() {
        dbFile.delete();
    }

    @Test
    public void testInsertAndSelectHighScore() {
        int id = highScoreTable.insertHighScore(1, "testUser", 100L);
        assertEquals(1, id);

        List<HighScore> highScores = highScoreTable.selectBestScoresForLevel(1, 10);
        assertEquals(1, highScores.size());
        assertEquals("testUser", highScores.get(0).getUsername());
        assertEquals(100L, highScores.get(0).getTime());
    }

    @Test
    public void testInsertAndReadHighScores() {
        highScoreTable.insertHighScore(1, "user1", 300L);
        highScoreTable.insertHighScore(2, "user2", 200L);
        highScoreTable.insertHighScore(3, "user3", 100L);

        List<HighScore> highScoreList = new ArrayList<>();
        highScoreTable.readHighScores((id, levelId, username, time) -> highScoreList.add(
                new HighScore(id, levelId, username, time)));

        assertEquals(3, highScoreList.size());
        assertEquals(1, highScoreList.get(0).getScoreId());
        assertEquals("user1", highScoreList.get(0).getUsername());
        assertEquals(300L, highScoreList.get(0).getTime());

        assertEquals(2, highScoreList.get(1).getScoreId());
        assertEquals("user2", highScoreList.get(1).getUsername());
        assertEquals(200L, highScoreList.get(1).getTime());

        assertEquals(3, highScoreList.get(2).getScoreId());
        assertEquals("user3", highScoreList.get(2).getUsername());
        assertEquals(100L, highScoreList.get(2).getTime());
    }

    @Test
    public void testSelectHighScoresWhenEmpty() {
        List<HighScore> highScores = highScoreTable.selectBestScoresForLevel(1, 10);
        assertEquals(0, highScores.size());
    }

    @Test
    public void testInsertAndSelectBestScoreForUser() {
        highScoreTable.insertHighScore(1, "user", 100L);
        highScoreTable.insertHighScore(1, "user", 150L);

        HighScore highScore = highScoreTable.selectBestScoreForUserAndLevel(1, "user");

        assertNotNull(highScore);
        assertEquals(100L, highScore.getTime());
    }

    @Test
    public void testInsertAndSelectBestScoreForMultipleUsers() {
        highScoreTable.insertHighScore(1, "user1", 300L);
        highScoreTable.insertHighScore(1, "user2", 200L);
        highScoreTable.insertHighScore(1, "user3", 150L);

        HighScore user1Score = highScoreTable.selectBestScoreForUserAndLevel(1, "user1");
        assertNotNull(user1Score);
        assertEquals("user1", user1Score.getUsername());
        assertEquals(300L, user1Score.getTime());

        HighScore user2Score = highScoreTable.selectBestScoreForUserAndLevel(1, "user2");
        assertNotNull(user2Score);
        assertEquals("user2", user2Score.getUsername());
        assertEquals(200L, user2Score.getTime());

        HighScore user3Score = highScoreTable.selectBestScoreForUserAndLevel(1, "user3");
        assertNotNull(user3Score);
        assertEquals("user3", user3Score.getUsername());
        assertEquals(150L, user3Score.getTime());
    }
}
