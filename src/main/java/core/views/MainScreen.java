package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import core.general.UserControlsEnum;
import core.general.Observer;
import core.levels.LevelManager;
import core.db.app.HighScoreInteractor;
import core.user.UserInteractor;

import java.util.Objects;

public class MainScreen extends AbstractScreen implements Observer<UserControlsEnum> {

    private final LevelManager level;
    private final HighScoreInteractor highScoreInteractor;
    private final UserInteractor userInteractor;
    private boolean pause = false;

    public MainScreen(Stage stage, LevelManager level, HighScoreInteractor highscoreInteractor, UserInteractor userInteractor) {
        super(stage);
        this.level = level;
        this.highScoreInteractor = highscoreInteractor;
        this.userInteractor = userInteractor;
    }

    @Override
    public void show() {
        level.getInputController().addObserver(this);
        level.resume();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.step();

        if (level.isGameEnded()) {
            // TODO add dependent levelid and nickname
            highScoreInteractor.addHighScore(level.getLevelNumber(), userInteractor.getUserName(), (int) level.getTimePassed());
            this.notifyOrchestrator(ScreenEnum.ENDGAME);
        }

        if (pause) {
            this.notifyOrchestrator(ScreenEnum.PAUSE);
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
        // Level might outlive the screen when pausing
        level.pause();
        level.getInputController().removeObserver(this);
    }

    @Override
    public void respondToEvent(UserControlsEnum param) {
        if (Objects.requireNonNull(param) == UserControlsEnum.Pause) {
            pause = true;
        }
    }
}
