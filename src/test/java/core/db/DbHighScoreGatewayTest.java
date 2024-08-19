package core.db;

import core.db.database.DbHighScoreGateway;
import core.db.database.DbHighScoreTable;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DbHighScoreGatewayTest {

    @Test
    void testGetBestScoresForLevelSorted() {
        DbHighScoreTable table = mock(DbHighScoreTable.class);

        HighScore score1 = new HighScore(1, 1, "user1", Time.valueOf("01:23:45"));
        HighScore score2 = new HighScore(2, 1, "user2", Time.valueOf("02:23:45"));
        HighScore score3 = new HighScore(3, 1, "user3", Time.valueOf("00:23:45"));
        List<HighScore> sortedScores = List.of(score3, score1, score2);

        when(table.selectBestScoresForLevel(1, 3)).thenReturn(sortedScores);

        DbHighScoreGateway gateway = new DbHighScoreGateway(table);
        List<HighScore> result = gateway.getBestScoresForLevel(1, 3);

        assertThat(result).isSortedAccordingTo((s1, s2) -> s1.getTime().compareTo(s2.getTime()));
    }
}
