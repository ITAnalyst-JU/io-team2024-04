package core.db;

import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HighScoreTest {
    @Test
    void testCreate() {
        var highScore = new HighScore(1, 101, "player1", 5000L);
        assertThat(highScore.getScoreId()).isEqualTo(1);
        assertThat(highScore.getLevelId()).isEqualTo(101);
        assertThat(highScore.getUsername()).isEqualTo("player1");
        assertThat(highScore.getTime()).isEqualTo(5000L);
    }
}
