package core.db;

import core.db.app.HighScoreGateway;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HighScoreInteractorWithGatewayTest {
    @Test
    void testAddHighScore() {
        var gateway = mock(HighScoreGateway.class);
        var highScore = new HighScore(1, 1, "sigma", 1000L);
        when(gateway.addHighScore( 1, "sigma", 1000)).thenReturn(highScore);

        var interactor = new HighScoreInteractorWithGateway(gateway);
        HighScore result = interactor.addHighScore( 1, "sigma", 1000L);

        verify(gateway).addHighScore(1, "sigma", 1000L);
        assertThat(result).isSameAs(highScore);
    }

    @Test
    void testGetBestScoresForLevel() {
        HighScoreGateway gateway = mock(HighScoreGateway.class);
        HighScore highScore1 = new HighScore(1, 101, "player1", 4000L);
        HighScore highScore2 = new HighScore(2, 101, "player2", 4500L);

        when(gateway.getBestScoresForLevel(101, 2)).thenReturn(List.of(highScore1, highScore2));

        var interactor = new HighScoreInteractorWithGateway(gateway);
        List<HighScore> highScores = interactor.getBestScoresForLevel(101, 2);

        verify(gateway).getBestScoresForLevel(101, 2);
        assertThat(highScores).containsExactly(highScore1, highScore2);
    }

    @Test
    void testGetBestScoreForUserAndLevel() {
        HighScoreGateway gateway = mock(HighScoreGateway.class);
        HighScore expectedHighScore = new HighScore(1, 101, "player1", 4000L);
        when(gateway.getBestScoreForUserAndLevel(101, "player1")).thenReturn(expectedHighScore);

        var interactor = new HighScoreInteractorWithGateway(gateway);
        HighScore result = interactor.getBestScoreForUserAndLevel(101, "player1");

        verify(gateway).getBestScoreForUserAndLevel(101, "player1");
        assertThat(result).isSameAs(expectedHighScore);
    }
}
