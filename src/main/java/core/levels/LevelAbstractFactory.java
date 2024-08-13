package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import core.entities.Player;

public class LevelAbstractFactory {
    public AbstractLevel createLevel(int levelNumber) {
//        if (levelNumber == 1) {
//            TiledMap map = new TmxMapLoader().load("map/map.tmx");
//            OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);
//            OrthographicCamera camera = new OrthographicCamera();
//            Player player = new Player(new Sprite(new Texture("player/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
//            Gdx.input.setInputProcessor(player);
//            player.setPosition(100, 490);
//            long beginTime = TimeUtils.millis();
//            return new AbstractLevel(map, renderer, camera, player, beginTime);
//        }
        return null;
    }
}
