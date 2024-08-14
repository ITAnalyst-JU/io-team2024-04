package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.general.Observable;
import core.general.Observer;

public class AbstractScreen extends Observable<Observer<ScreenEnum>> implements Screen {

    // Generated cycle in class dependency graph
    // observer pattern used
    // dependency inversion principle applied ;)
    protected Stage stage;

    public AbstractScreen(Stage stage) {
        this.stage = stage;
    }

    void notifyOrchestrator(ScreenEnum screenEnum) {
        notifyObservers(observer -> observer.respondToEvent(screenEnum));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
