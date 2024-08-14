package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import core.entities.Player;
import core.utilities.Constants;

public class LevelAbstractFactory {
    public AbstractLevel createLevel(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> new AbstractLevel(new Vector2(110/ Constants.Physics.Scale, 500/Constants.Physics.Scale), "map/map2.tmx");
            case 2 -> null;
            default -> null;
        };
    }
}
