package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.entities.Player;
public class AbstractLevel {
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected OrthographicCamera camera;

    protected Player player;
    protected long beginTime;

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public AbstractLevel(
        TiledMap map,
        OrthogonalTiledMapRenderer renderer,
        OrthographicCamera camera,
        Player player,
        long beginTime) {
        this.map = map;
        this.renderer = renderer;
        this.camera = camera;
        this.player = player;
        this.beginTime = beginTime;
    }
}
