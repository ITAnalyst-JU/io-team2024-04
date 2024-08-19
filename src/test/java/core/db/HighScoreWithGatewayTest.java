package core.db;

import core.db.app.HighScoreGateway;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HighScoreWithGatewayTest {
    @Test
    void testAddHighScore() {
        var gateway = mock(HighScoreGateway.class);
        var highScore = new HighScore(1, 1, "sigma", Time.valueOf("01:23:45"));
        when(gateway.addHighScore( 1, "sigma", Time.valueOf("01:23:45"))).thenReturn(highScore);
        HighScoreInteractorWithGateway interactor = new HighScoreInteractorWithGateway(gateway);
        HighScore result = interactor.addHighScore( 1, "sigma", Time.valueOf("01:23:45"));
        assertThat(result).isSameAs(highScore);
    }

    @Test
    void testGetBestScoresForLevel() {
        var gateway = mock(HighScoreGateway.class);

        HighScore score1 = new HighScore(1, 1, "user1", Time.valueOf("01:23:45"));
        HighScore score2 = new HighScore(2, 1, "user2", Time.valueOf("00:23:45"));
        HighScore score3 = new HighScore(3, 1, "user3", Time.valueOf("02:23:45"));
        List<HighScore> bestScores = List.of(score1, score2, score3);

        when(gateway.getBestScoresForLevel(1, 3)).thenReturn(bestScores);
        HighScoreInteractorWithGateway interactor = new HighScoreInteractorWithGateway(gateway);
        List<HighScore> result = interactor.getBestScoresForLevel(1,3);
        assertThat(result).isSameAs(bestScores);
        verify(gateway).getBestScoresForLevel(1,3);
    }
}
