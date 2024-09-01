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
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.show();

        Mockito.verify(Gdx.input).setInputProcessor(stage);
    }

    @Test
    public void testRender() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        Gdx.gl = Mockito.mock(com.badlogic.gdx.graphics.GL20.class);
        Gdx.graphics = Mockito.mock(com.badlogic.gdx.Graphics.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.render(0.0f);

        Mockito.verify(stage).act(0.0f);
        Mockito.verify(stage).draw();
    }

    @Test
    public void testDispose() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.dispose();

        Mockito.verify(stage).dispose();
    }

    @Test
    public void testNotifyOrchestrator() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var observer = (Observer<ScreenEnum>) Mockito.mock(core.general.Observer.class);
        var screenEnum = Mockito.mock(core.views.ScreenEnum.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.addObserver(observer);
        abstractScreen.notifyOrchestrator(screenEnum);

        Mockito.verify(observer).respondToEvent(screenEnum);
    }

    @Test
    public void testResize() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.resize(0, 0);

        Mockito.verifyNoInteractions(stage);
    }

    @Test
    public void testPause() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.pause();

        Mockito.verifyNoInteractions(stage);
    }

    @Test
    public void testResume() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.resume();

        Mockito.verifyNoInteractions(stage);
    }

    @Test
    public void testHide() {
        var stage = Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class);
        var assetManagerFactory = Mockito.mock(core.assets.AssetManagerFactory.class);

        AbstractScreen abstractScreen = new AbstractScreen(stage, assetManagerFactory);
        abstractScreen.hide();

        Mockito.verifyNoInteractions(stage);
    }
}
