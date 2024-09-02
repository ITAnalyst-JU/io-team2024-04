package core.db;

import core.db.database.DbHighScoreGateway;
import core.db.database.DbHighScoreTable;
import core.db.domain.HighScore;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DbHighScoreGatewayTest {
    @Test
    void testAddHighScore() {
        var table = mock(DbHighScoreTable.class);
        when(table.insertHighScore(101, "player1", 5000L)).thenReturn(42);

        var gateway = new DbHighScoreGateway(table);
        HighScore highScore = gateway.addHighScore(101, "player1", 5000L);

        verify(table).insertHighScore(101, "player1", 5000L);
        assertThat(highScore).isNotNull();
        assertThat(highScore.getScoreId()).isEqualTo(42);
        assertThat(highScore.getLevelId()).isEqualTo(101);
        assertThat(highScore.getUsername()).isEqualTo("player1");
        assertThat(highScore.getTime()).isEqualTo(5000L);
    }

    @Test
    void testGetBestScoresForLevel() {
        var table = mock(DbHighScoreTable.class);
        when(table.selectBestScoresForLevel(1, 5)).thenReturn(List.of(
                new HighScore(1, 1, "player1", 1000L),
                new HighScore(2, 1, "player2", 500L)
        ));
        var gateway = new DbHighScoreGateway(table);

        List<HighScore> scores = gateway.getBestScoresForLevel(1, 5);

        verify(table).selectBestScoresForLevel(1, 5);
        assertThat(scores).isNotNull();
        assertThat(scores).hasSize(2);
        assertThat(scores.get(0).getScoreId()).isEqualTo(1);
        assertThat(scores.get(0).getLevelId()).isEqualTo(1);
        assertThat(scores.get(0).getUsername()).isEqualTo("player1");
        assertThat(scores.get(0).getTime()).isEqualTo(1000L);
        assertThat(scores.get(1).getScoreId()).isEqualTo(2);
        assertThat(scores.get(1).getLevelId()).isEqualTo(1);
        assertThat(scores.get(1).getUsername()).isEqualTo("player2");
        assertThat(scores.get(1).getTime()).isEqualTo(500L);
    }

    @Test
    void testGetBestScoresForLevelWhenNoScores() {
        DbHighScoreTable mockTable = mock(DbHighScoreTable.class);
        when(mockTable.selectBestScoresForLevel(1, 5)).thenReturn(Collections.emptyList());
        DbHighScoreGateway gateway = new DbHighScoreGateway(mockTable);

        List<HighScore> scores = gateway.getBestScoresForLevel(1, 5);

        verify(mockTable).selectBestScoresForLevel(1, 5);
        assertThat(scores).isEmpty();
    }

    @Test
    void testGetBestScoreForUserAndLevel() {
        var table = mock(DbHighScoreTable.class);
        when(table.selectBestScoreForUserAndLevel(1, "player1")).thenReturn(
                new HighScore(42, 1, "player1", 1000L)
        );
        var gateway = new DbHighScoreGateway(table);

        HighScore highScore = gateway.getBestScoreForUserAndLevel(1, "player1");

        verify(table).selectBestScoreForUserAndLevel(1, "player1");
        assertThat(highScore).isNotNull();
        assertThat(highScore.getScoreId()).isEqualTo(42);
        assertThat(highScore.getLevelId()).isEqualTo(1);
        assertThat(highScore.getUsername()).isEqualTo("player1");
        assertThat(highScore.getTime()).isEqualTo(1000L);
    }

    @Test
    void testGetBestScoreForUserAndLevelWhenNoScore() {
        DbHighScoreTable mockTable = mock(DbHighScoreTable.class);
        when(mockTable.selectBestScoreForUserAndLevel(1, "player1")).thenReturn(null);
        DbHighScoreGateway gateway = new DbHighScoreGateway(mockTable);

        HighScore highScore = gateway.getBestScoreForUserAndLevel(1, "player1");

        verify(mockTable).selectBestScoreForUserAndLevel(1, "player1");
        assertThat(highScore).isNull();
    }
}
