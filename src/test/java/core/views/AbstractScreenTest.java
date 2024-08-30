package core.views;

import com.badlogic.gdx.Gdx;
import core.general.Observer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class AbstractScreenTest {
    @Test
    public void testShow() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        Gdx.input = Mockito.mock(com.badlogic.gdx.Input.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage);
        abstractScreen.show();

        Mockito.verify(Gdx.input).setInputProcessor(stage);
    }

    @Test
    public void testRender() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        Gdx.gl = Mockito.mock(com.badlogic.gdx.graphics.GL20.class);
        Gdx.graphics = Mockito.mock(com.badlogic.gdx.Graphics.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage);
        abstractScreen.render(0.0f);

        Mockito.verify(stage).act(0.0f);
        Mockito.verify(stage).draw();
    }

    @Test
    public void testDispose() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage);
        abstractScreen.dispose();

        Mockito.verify(stage).dispose();
    }

    @Test
    public void testNotifyOrchestrator() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var observer = (Observer<ScreenEnum>) Mockito.mock(core.general.Observer.class);
        var screenEnum = Mockito.mock(core.views.ScreenEnum.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage);
        abstractScreen.addObserver(observer);
        abstractScreen.notifyOrchestrator(screenEnum);

        Mockito.verify(observer).respondToEvent(screenEnum);
    }
}
