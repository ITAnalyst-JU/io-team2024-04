package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import core.levels.AbstractLevel;

public class MainScreen extends AbstractScreen {

    private final AbstractLevel level;

    public MainScreen(Stage stage, AbstractLevel level) {
        super(stage);
        this.level = level;
    }

    @Override
    public void show() {
        level.create();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.step();

        if (level.isGameEnded()) {
            this.notifyOrchestrator(ScreenEnum.MENU);
        }
    }

    @Override
    public void resize(int i, int i1) {
        level.resize(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        level.dispose();
    }
}
