package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.EntityManager;
import core.entities.IEntity;
import core.entities.Player;
import core.general.UserInputController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

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
        var buttonActions = (Map<Integer, IEntity>)Mockito.mock(Map.class);
        var inputController = Mockito.mock(UserInputController.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        levelManager.create();

        // All of those object should be in good state from the factory.
        Mockito.verifyNoInteractions(map, renderer, camera, world, entityManager, player, contactListener, buttonActions);
    }

    @Test
    public void testResume() {
        var map = Mockito.mock(TiledMap.class);
        var renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        var camera = Mockito.mock(OrthographicCamera.class);
        var world = Mockito.mock(World.class);
        var entityManager = Mockito.mock(EntityManager.class);
        var player = Mockito.mock(Player.class);
        var contactListener = Mockito.mock(LevelContactListener.class);
        var buttonActions = (Map<Integer, IEntity>) Mockito.mock(Map.class);
        var inputController = Mockito.mock(UserInputController.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();

        levelManager.resume();

        Mockito.verify(player).resetControls();
        Mockito.verify(Gdx.input).setInputProcessor(inputController);
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
        var buttonActions = (Map<Integer, IEntity>)Mockito.mock(Map.class);
        var inputController = Mockito.mock(UserInputController.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        levelManager.create();
        levelManager.dispose();

        Mockito.verify(world).dispose();
        Mockito.verify(renderer).dispose();
        Mockito.verify(map).dispose();
        Mockito.verifyNoMoreInteractions(world, renderer, map);
        Mockito.verifyNoInteractions(camera, entityManager, player, contactListener, buttonActions);
    }

    @Test
    public void testInputController() {
        var map = Mockito.mock(TiledMap.class);
        var renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        var camera = Mockito.mock(OrthographicCamera.class);
        var world = Mockito.mock(World.class);
        var entityManager = Mockito.mock(EntityManager.class);
        var player = Mockito.mock(Player.class);
        var contactListener = Mockito.mock(LevelContactListener.class);
        var buttonActions = (Map<Integer, IEntity>) Mockito.mock(Map.class);
        var inputController = Mockito.mock(UserInputController.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenInputController = levelManager.getInputController();

        Assertions.assertEquals(inputController, gottenInputController);
    }

    @Test
    public void testLevelNumber() {
        var map = Mockito.mock(TiledMap.class);
        var renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        var camera = Mockito.mock(OrthographicCamera.class);
        var world = Mockito.mock(World.class);
        var entityManager = Mockito.mock(EntityManager.class);
        var player = Mockito.mock(Player.class);
        var contactListener = Mockito.mock(LevelContactListener.class);
        var buttonActions = (Map<Integer, IEntity>) Mockito.mock(Map.class);
        var inputController = Mockito.mock(UserInputController.class);
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 7);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenLevelNumber = levelManager.getLevelNumber();

        Assertions.assertEquals(7, gottenLevelNumber);
    }
}
