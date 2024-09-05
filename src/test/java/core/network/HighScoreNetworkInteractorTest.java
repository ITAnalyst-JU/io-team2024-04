package core.network;

import core.db.domain.HighScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class HighScoreNetworkInteractorTest {
    private HighScoreClient highScoreClient;
    private HighScoreNetworkInteractor highScoreNetworkInteractor;

    @BeforeEach
    public void setUp() {
        highScoreClient = mock(HighScoreClient.class);
        highScoreNetworkInteractor = new HighScoreNetworkInteractor(highScoreClient);
    }

    @Test
    public void testAddHighScoreSuccess() {
        ArgumentCaptor<HighScoreClient.Callback<Void>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);

        highScoreNetworkInteractor.addHighScore(1, "Player1", 1000L, new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(Void result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).addHighScore(eq(1), eq("Player1"), eq(1000L), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(null);
    }

    @Test
    public void testAddHighScoreError() {
        ArgumentCaptor<HighScoreClient.Callback<Void>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);

        highScoreNetworkInteractor.addHighScore(1, "Player1", 1000L, new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(Void result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).addHighScore(eq(1), eq("Player1"), eq(1000L), callbackCaptor.capture());
        callbackCaptor.getValue().onError("Network error");
    }

    @Test
    public void testGetBestScoresSuccess() {
        ArgumentCaptor<HighScoreClient.Callback<List<HighScore>>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);
        List<HighScore> mockScores = new ArrayList<>();
        mockScores.add(new HighScore(1, 1, "Player1", 1000L));

        highScoreNetworkInteractor.getBestScores(1, 10, new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(List<HighScore> result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).getTopScores(eq(1), eq(10), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(mockScores);
    }

    @Test
    public void testGetBestScoresError() {
        ArgumentCaptor<HighScoreClient.Callback<List<HighScore>>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);

        highScoreNetworkInteractor.getBestScores(1, 10, new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(List<HighScore> result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).getTopScores(eq(1), eq(10), callbackCaptor.capture());
        callbackCaptor.getValue().onError("Network error");
    }

    @Test
    public void testGetBestScoreForUserSuccess() {
        ArgumentCaptor<HighScoreClient.Callback<HighScore>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);
        HighScore mockScore = new HighScore(1, 1, "Player1", 1000L);

        highScoreNetworkInteractor.getBestScoreForUser(1, "Player1", new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(HighScore result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).getBestScoreForUser(eq(1), eq("Player1"), callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(mockScore);
    }

    @Test
    public void testGetBestScoreForUserError() {
        ArgumentCaptor<HighScoreClient.Callback<HighScore>> callbackCaptor = ArgumentCaptor.forClass(HighScoreClient.Callback.class);

        highScoreNetworkInteractor.getBestScoreForUser(1, "Player1", new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(HighScore result) {}
            @Override
            public void onError(String errorMessage) {}
        });

        verify(highScoreClient).getBestScoreForUser(eq(1), eq("Player1"), callbackCaptor.capture());
        callbackCaptor.getValue().onError("Network error");
    }
}
