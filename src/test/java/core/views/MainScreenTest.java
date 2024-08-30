package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.db.app.HighScoreInteractor;
import core.general.Observer;
import core.levels.LevelManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class MainScreenTest {

    @Test
    public void testShow() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        mainScreen.show();
        Mockito.verify(level).create();
    }

    @Test
    public void testRender() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(false);
        Gdx.gl = Mockito.mock(GL20.class);

        mainScreen.render(0.0f);
        Mockito.verify(level).step();
        Mockito.verify(level).isGameEnded();
        Mockito.verifyNoMoreInteractions(level);
        Mockito.verifyNoInteractions(stage, highScoreInteractor);
    }

    @Test
    public void testGameEnded() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(true);
        Mockito.when(level.getTimePassed()).thenReturn(17L);
        Gdx.gl = Mockito.mock(GL20.class);

        mainScreen.render(0.0f);
        Mockito.verify(level).isGameEnded();
        Mockito.verify(level).getTimePassed();
        Mockito.verify(highScoreInteractor).addHighScore(Mockito.anyInt(), Mockito.anyString(), Mockito.eq(17L));
    }

    @Test
    public void testGameEndedAdvanced() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(true);
        Mockito.when(level.getTimePassed()).thenReturn(17L);
        Gdx.gl = Mockito.mock(GL20.class);
        var observer = (Observer<ScreenEnum>) Mockito.mock(Observer.class);
        mainScreen.addObserver(observer);

        mainScreen.render(0.0f);

        Mockito.verify(observer).respondToEvent(ScreenEnum.ENDGAME);
    }
}
