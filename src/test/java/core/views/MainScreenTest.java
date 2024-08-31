package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.db.app.HighScoreInteractor;
import core.general.Observer;
import core.general.UserControlsEnum;
import core.general.UserInputController;
import core.levels.LevelManager;
import core.user.UserInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class MainScreenTest {

    @Test
    public void testShow() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var userInteractor = Mockito.mock(UserInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor, userInteractor);
        var inputController = Mockito.mock(UserInputController.class);
        Mockito.when(level.getInputController()).thenReturn(inputController);

        mainScreen.show();

        Mockito.verify(level).resume();
        Mockito.verify(inputController).addObserver(mainScreen);
    }

    @Test
    public void testRender() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var userInteractor = Mockito.mock(UserInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor, userInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(false);
        Gdx.gl = Mockito.mock(GL20.class);

        mainScreen.render(0.0f);
        Mockito.verify(level).step();
        Mockito.verify(level).isGameEnded();
        Mockito.verifyNoMoreInteractions(level);
        Mockito.verifyNoInteractions(stage, highScoreInteractor, userInteractor);
    }

    @Test
    public void testGameEnded() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var userInteractor = Mockito.mock(UserInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor, userInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(true);
        Mockito.when(level.getTimePassed()).thenReturn(17L);
        Mockito.when(userInteractor.getUserName()).thenReturn("Player1");
        Mockito.when(level.getLevelNumber()).thenReturn(3);
        Gdx.gl = Mockito.mock(GL20.class);

        mainScreen.render(0.0f);
        Mockito.verify(level).isGameEnded();
        Mockito.verify(level).getTimePassed();
        Mockito.verify(highScoreInteractor).addHighScore(Mockito.anyInt(), Mockito.eq("Player1"), Mockito.eq(17L));
        Mockito.verify(highScoreInteractor).addHighScore(Mockito.eq(3), Mockito.anyString(), Mockito.eq(17L));
    }

    @Test
    public void testGameEndedAdvanced() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var userInteractor = Mockito.mock(UserInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor, userInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(true);
        Mockito.when(level.getTimePassed()).thenReturn(17L);
        Mockito.when(level.getLevelNumber()).thenReturn(0);
        Gdx.gl = Mockito.mock(GL20.class);
        var observer = (Observer<ScreenEnum>) Mockito.mock(Observer.class);
        mainScreen.addObserver(observer);

        mainScreen.render(0.0f);

        Mockito.verify(observer).respondToEvent(ScreenEnum.ENDGAME);
    }

    @Test
    public void testGamePause() {

        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(false);
        Gdx.gl = Mockito.mock(GL20.class);
        var observer = (Observer<ScreenEnum>) Mockito.mock(Observer.class);
        mainScreen.addObserver(observer);

        mainScreen.respondToEvent(UserControlsEnum.Pause);
        mainScreen.render(0.0f);

        Mockito.verify(observer).respondToEvent(ScreenEnum.PAUSE);
    }

    @Test
    public void testDispose() {
        var stage = Mockito.mock(Stage.class);
        var level = Mockito.mock(LevelManager.class);
        var highScoreInteractor = Mockito.mock(HighScoreInteractor.class);
        var mainScreen = new MainScreen(stage, level, highScoreInteractor);
        Mockito.when(level.isGameEnded()).thenReturn(false);
        var inputController = Mockito.mock(UserInputController.class);
        Mockito.when(level.getInputController()).thenReturn(inputController);
        Gdx.gl = Mockito.mock(GL20.class);

        mainScreen.render(0.0f);
        mainScreen.dispose();

        Mockito.verify(level).step();
        Mockito.verify(level).isGameEnded();
        Mockito.verify(level).pause();
        Mockito.verify(level).getInputController();
        Mockito.verify(inputController).removeObserver(mainScreen);
        Mockito.verifyNoMoreInteractions(level, inputController);
    }
}
