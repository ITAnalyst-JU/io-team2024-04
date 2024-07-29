package core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.Program;

public class OptionsScreen implements Screen {
    private final Program game;
    public OptionsScreen(final Program game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.6f, 0.1f, 1);

        game.batch.begin();
        game.font.draw(game.batch, "Tutaj som opcje looot :0", 100, 150);
        game.batch.end();
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

    }
}
