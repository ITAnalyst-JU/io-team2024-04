package core.db;

import core.db.app.HighScoreGateway;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HighScoreWithGatewayTest {
    @Test
    void testAddHighScore() {
        var gateway = mock(HighScoreGateway.class);
        var highScore = new HighScore(1, 1, "sigma", 1000);
        when(gateway.addHighScore( 1, "sigma", 1000)).thenReturn(highScore);
        HighScoreInteractorWithGateway interactor = new HighScoreInteractorWithGateway(gateway);
        HighScore result = interactor.addHighScore( 1, "sigma", 1000);
        assertThat(result).isSameAs(highScore);
    }

    @Test
    void testGetBestScoresForLevel() {
        var gateway = mock(HighScoreGateway.class);

        HighScore score1 = new HighScore(1, 1, "user1", 1000);
        HighScore score2 = new HighScore(2, 1, "user2", 100);
        HighScore score3 = new HighScore(3, 1, "user3", 10000);
        List<HighScore> bestScores = List.of(score1, score2, score3);

        when(gateway.getBestScoresForLevel(1, 3)).thenReturn(bestScores);
        HighScoreInteractorWithGateway interactor = new HighScoreInteractorWithGateway(gateway);
        List<HighScore> result = interactor.getBestScoresForLevel(1,3);
        assertThat(result).isSameAs(bestScores);
        verify(gateway).getBestScoresForLevel(1,3);
    }
}
