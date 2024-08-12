package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.orchestrator.SupremeOrchestrator;

public class AbstractScreen implements Screen {
    // Generates cycle in class dependency graph
    // TODO: think about observer
    private final SupremeOrchestrator orchestrator;
    protected Stage stage;

    public AbstractScreen(SupremeOrchestrator supremeOrchestrator) {
        stage = new Stage(new ScreenViewport()); // TODO: dependency injection
        this.orchestrator = supremeOrchestrator;
    }

    // foundations for observer pattern
    public void notifyOrchestator(ScreenState screenState) {
        orchestrator.changeScreen(screenState);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
