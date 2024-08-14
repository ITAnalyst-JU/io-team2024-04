package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.AbstractEntity;
import core.entities.Player;
import core.utilities.WorldContactListener;

import java.util.List;

public class AbstractLevel {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private World world;

    private Player player;
    private long beginTime;

    private final Vector2 playerBeginPosition;
    private final String mapName;

    private WorldContactListener contactListener;

    //TODO: Find better solution
    private List<AbstractEntity> entities;

    public AbstractLevel(Vector2 playerBeginPosition, String mapName) {
        this.playerBeginPosition = playerBeginPosition;
        this.mapName = mapName;
    }

    public void dispose() {
        // TODO: this sequence breaks game, free resources properly
        // renderer.dispose();
        // map.dispose();
        // world.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public Vector2 getPlayerBeginPosition() {
        return playerBeginPosition;
    }

    public String getMapName() {
        return mapName;
    }

    public WorldContactListener getContactListener() {
        return contactListener;
    }

    public void setContactListener(WorldContactListener contactListener) {
        this.contactListener = contactListener;
    }

    public List<AbstractEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }
}
