package core.views;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import core.entities.AbstractEntity;
import core.entities.BasicEnemy;
import core.entities.MovingEnemy;
import core.entities.MovingPlatform;
import core.entities.MovingPlatform.MovementDirection;
import core.entities.Player;
import core.levels.AbstractLevel;
import core.utilities.Constants;
import core.utilities.WorldContactListener;
import core.utilities.Constants.Physics;

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
