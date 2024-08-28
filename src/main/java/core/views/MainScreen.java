package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import core.levels.AbstractLevel;
import core.db.app.HighScoreInteractor;

public class MainScreen extends AbstractScreen {

    private final AbstractLevel level;
    private final HighScoreInteractor highScoreInteractor;

    public MainScreen(Stage stage, AbstractLevel level, HighScoreInteractor highscoreInteractor) {
        super(stage);
        this.level = level;
        this.highScoreInteractor = highscoreInteractor;
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
            // TODO add dependent levelid and nickname
            highScoreInteractor.addHighScore(1,"bob", (int) level.getTimePassed());
            this.notifyOrchestrator(ScreenEnum.ENDGAME);
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
