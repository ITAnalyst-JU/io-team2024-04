package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.EntityManager;
import core.entities.IEntity;
import core.entities.Player;
import core.general.UserInputController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

@SuppressWarnings("unchecked")
public class LevelManagerTest {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    World world;
    EntityManager entityManager;
    Player player;
    LevelContactListener contactListener;
    Map<Integer, IEntity> buttonActions;
    UserInputController inputController;
    Batch batch;

    @BeforeEach
    void setUp() {
        map = Mockito.mock(TiledMap.class);
        renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        camera = Mockito.mock(OrthographicCamera.class);
        world = Mockito.mock(World.class);
        entityManager = Mockito.mock(EntityManager.class);
        player = Mockito.mock(Player.class);
        contactListener = Mockito.mock(LevelContactListener.class);
        buttonActions = (Map<Integer, IEntity>)Mockito.mock(Map.class);
        inputController = Mockito.mock(UserInputController.class);
        batch = Mockito.mock(Batch.class);
        Mockito.when(renderer.getBatch()).thenReturn(batch);
    }

    @Test
    void testCreate() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        levelManager.create();

        // All of those object should be in good state from the get-go.
        Mockito.verifyNoInteractions(map, renderer, camera, world, entityManager, player, contactListener, buttonActions);
    }

    @Test
    void testResume() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();

        levelManager.resume();

        Mockito.verify(player).resetControls();
        Mockito.verify(Gdx.input).setInputProcessor(inputController);
    }

    @Test
    void testDispose() {
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
    void testInputController() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenInputController = levelManager.getInputController();

        Assertions.assertThat(gottenInputController).isSameAs(inputController);
    }

    @Test
    void testLevelNumber() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 7);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenLevelNumber = levelManager.getLevelNumber();

        Assertions.assertThat(gottenLevelNumber).isEqualTo(7);
    }

    @Test
    void testResize() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);

        levelManager.create();
        levelManager.resize(100, 200);

        Assertions.assertThat(camera.viewportWidth).isEqualTo(100f/3f);
        Assertions.assertThat(camera.viewportHeight).isEqualTo(200f/3f);
        Mockito.verify(camera).update();
    }

    @Test
    void testGetTimePassed() {
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);
        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var timePassed = levelManager.getTimePassed();

        // Unfortunately, it will always be 0 in testing.
        Assertions.assertThat(timePassed).isEqualTo(0);
    }

    @Test
    void testUpdate() {
        camera = Mockito.spy(new OrthographicCamera());
        var levelManager = new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputController, 0);
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(player.getPosition()).thenReturn(new Vector2(0, 0));
        Mockito.doNothing().when(camera).update();

        levelManager.step();
        Mockito.verify(world).step(Mockito.anyFloat(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(entityManager).update();
        Mockito.verify(camera).update();
        Mockito.verify(renderer).setView(camera);
        Mockito.verify(renderer).render();
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verify(entityManager).render(batch);
    }
}
