package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.ButtonAction;
import core.entities.EntityManager;
import core.entities.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class LevelManagerTest {

    @Test
    public void testCreate() {
        var map = Mockito.mock(TiledMap.class);
        var renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        var camera = Mockito.mock(OrthographicCamera.class);
        var world = Mockito.mock(World.class);
        var entityManager = Mockito.mock(EntityManager.class);
        var player = Mockito.mock(Player.class);
        var contactListener = Mockito.mock(LevelContactListener.class);
        var buttonActions = (Map<Integer, ButtonAction>)Mockito.mock(Map.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions);

        levelManager.create();

        // All of those object should be in good state from the factory.
        Mockito.verifyNoInteractions(map, renderer, camera, world, entityManager, player, contactListener, buttonActions);
    }

    @Test
    public void testDispose() {
        var map = Mockito.mock(TiledMap.class);
        var renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        var camera = Mockito.mock(OrthographicCamera.class);
        var world = Mockito.mock(World.class);
        var entityManager = Mockito.mock(EntityManager.class);
        var player = Mockito.mock(Player.class);
        var contactListener = Mockito.mock(LevelContactListener.class);
        var buttonActions = (Map<Integer, ButtonAction>)Mockito.mock(Map.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions);

        levelManager.create();
        levelManager.dispose();

        Mockito.verify(world).dispose();
        Mockito.verify(renderer).dispose();
        Mockito.verify(map).dispose();
        Mockito.verifyNoMoreInteractions(world, renderer, map);
        Mockito.verifyNoInteractions(camera, entityManager, player, contactListener, buttonActions);
    }

}
