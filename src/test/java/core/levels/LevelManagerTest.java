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
import com.badlogic.gdx.utils.viewport.Viewport;
import core.entities.IEntityManager;
import core.entities.IEntity;
import core.entities.Player;
import core.general.UserInputController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class LevelManagerTest {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    World world;
    IEntityManager IEntityManager;
    Player player;
    LevelContactListener contactListener;
    Map<Integer, IEntity> buttonActions;
    UserInputController inputController;
    Batch batch;
    OrthogonalTiledMapRenderer backgroundRenderer;
    Viewport backgroundViewport;

    @BeforeEach
    void setUp() {
        map = Mockito.mock(TiledMap.class);
        renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        camera = Mockito.mock(OrthographicCamera.class);
        world = Mockito.mock(World.class);
        IEntityManager = Mockito.mock(IEntityManager.class);
        player = Mockito.mock(Player.class);
        contactListener = Mockito.mock(LevelContactListener.class);
        buttonActions = (Map<Integer, IEntity>)Mockito.mock(Map.class);
        inputController = Mockito.mock(UserInputController.class);
        batch = Mockito.mock(Batch.class);
        backgroundRenderer = Mockito.mock(OrthogonalTiledMapRenderer.class);
        backgroundViewport = Mockito.mock(Viewport.class);

        Mockito.when(renderer.getBatch()).thenReturn(batch);
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(player.getPosition()).thenReturn(new Vector2(0, 0));
        camera = Mockito.spy(new OrthographicCamera());
        Mockito.doNothing().when(camera).update();
        Gdx.input = Mockito.mock(Input.class);
    }

    @Test
    void testCreate() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        levelManager.create();

        // All of those object should be in good state from the get-go.
        Mockito.verifyNoInteractions(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions);
    }

    @Test
    void testResume() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        Gdx.input = Mockito.mock(Input.class);

        levelManager.create();

        levelManager.resume();

        Mockito.verify(player).resetControls();
        Mockito.verify(Gdx.input).setInputProcessor(inputController);
    }

    @Test
    void testDispose() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        levelManager.create();
        levelManager.dispose();

        Mockito.verify(world).dispose();
        Mockito.verify(renderer).dispose();
        Mockito.verify(map).dispose();
        Mockito.verifyNoMoreInteractions(world, renderer, map);
        Mockito.verifyNoInteractions(camera, IEntityManager, player, contactListener, buttonActions);
    }

    @Test
    void testInputController() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenInputController = levelManager.getInputController();

        Assertions.assertThat(gottenInputController).isSameAs(inputController);
    }

    @Test
    void testLevelNumber() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 7, backgroundRenderer, backgroundViewport);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var gottenLevelNumber = levelManager.getLevelNumber();

        Assertions.assertThat(gottenLevelNumber).isEqualTo(7);
    }

    @Test
    void testResize() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        levelManager.create();
        levelManager.resize(100, 200);

        Assertions.assertThat(camera.viewportWidth).isEqualTo(100f/3f);
        Assertions.assertThat(camera.viewportHeight).isEqualTo(200f/3f);
        Mockito.verify(camera).update();
        Mockito.verify(backgroundViewport).update(100, 200);
    }

    @Test
    void testGetTimePassed() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);

        levelManager.create();
        levelManager.resume();
        levelManager.pause();
        var timePassed = levelManager.getTimePassed();

        // Unfortunately, it should always be 0 in testing.
        Assertions.assertThat(timePassed).isEqualTo(0);
    }

    @Test
    void testStep() {
        List<LevelContactListener.Event> events = Mockito.spy(new ArrayList<>());
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Finish, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        var backgroundCamera = Mockito.mock(OrthographicCamera.class);
        Mockito.when(backgroundViewport.getCamera()).thenReturn(backgroundCamera);

        levelManager.step();
        Mockito.verify(world).step(Mockito.anyFloat(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(IEntityManager).update();
        Mockito.verify(camera).update();
        Mockito.verify(renderer).setView(camera);
        Mockito.verify(renderer).render();
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verify(IEntityManager).render(batch);
        Mockito.verify(contactListener).getEvents();
        Mockito.verify(events).clear();
        Mockito.verify(backgroundRenderer).setView(backgroundCamera);
        Mockito.verify(backgroundRenderer).render();
    }

    @Test
    void testGameEnded() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        Assertions.assertThat(levelManager.isGameEnded()).isFalse();
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Finish, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();
        var res = levelManager.isGameEnded();

        Assertions.assertThat(res).isTrue();
    }

    @Test
    void testCheckpoint() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Checkpoint, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();
        Mockito.verify(IEntityManager).remove(Mockito.any());
        Mockito.verify(IEntityManager).saveState();
    }

    @Test
    void testPlatformNotUserDamageable() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        IEntity entity = Mockito.mock(IEntity.class);
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Platform, entity));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        Mockito.when(entity.getUserDamageable()).thenReturn(false);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(entity).getUserDamageable();
        Mockito.verifyNoMoreInteractions(IEntityManager, entity);
    }

    @Test
    void testPlatformUserDamageableWithKill() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        IEntity entity = Mockito.mock(IEntity.class);
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Platform, entity));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        Mockito.when(entity.getUserDamageable()).thenReturn(true);
        Mockito.when(entity.damage()).thenReturn(true);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(entity).getUserDamageable();
        Mockito.verify(entity).damage();
        Mockito.verify(IEntityManager).remove(entity);
        Mockito.verifyNoMoreInteractions(IEntityManager, entity);
    }

    @Test
    void testPlatformUserDamageableWithNoKill() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        IEntity entity = Mockito.mock(IEntity.class);
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Platform, entity));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        Mockito.when(entity.getUserDamageable()).thenReturn(true);
        Mockito.when(entity.damage()).thenReturn(false);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(entity).getUserDamageable();
        Mockito.verify(entity).damage();
        Mockito.verifyNoMoreInteractions(IEntityManager, entity);
    }

    @Test
    void testDeath() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Death, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(IEntityManager).recoverState();
        Mockito.verifyNoMoreInteractions(IEntityManager);
    }

    @Test
    void testGravityReverse() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.GravityReverse, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).reverseGravity();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }

    @Test
    void testButtonWithObjectDeath() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        IEntity entity = Mockito.mock(IEntity.class);
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Button, entity));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        Mockito.when(entity.getButtonNumber()).thenReturn(0);
        IEntity entity2 = Mockito.mock(IEntity.class);
        Mockito.when(buttonActions.get(0)).thenReturn(entity2);
        Mockito.when(entity2.buttonAction(entity2)).thenReturn(true);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(entity).getButtonNumber();
        Mockito.verify(buttonActions).get(0);
        Mockito.verify(entity2).buttonAction(entity2);
        ArgumentCaptor<IEntity> entityCaptor = ArgumentCaptor.forClass(IEntity.class);
        Mockito.verify(IEntityManager, Mockito.times(2)).remove(entityCaptor.capture());
        Mockito.verifyNoMoreInteractions(IEntityManager, entity, buttonActions);
        Assertions.assertThat(entityCaptor.getAllValues()).containsExactlyInAnyOrder(entity, entity2);
    }

    @Test
    void testButtonWithNoObjectDeath() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        IEntity entity = Mockito.mock(IEntity.class);
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Button, entity));
        Mockito.when(contactListener.getEvents()).thenReturn(events);
        Mockito.when(entity.getButtonNumber()).thenReturn(0);
        IEntity entity2 = Mockito.mock(IEntity.class);
        Mockito.when(buttonActions.get(0)).thenReturn(entity2);
        Mockito.when(entity2.buttonAction(entity2)).thenReturn(false);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(entity).getButtonNumber();
        Mockito.verify(buttonActions).get(0);
        Mockito.verify(entity2).buttonAction(entity2);
        Mockito.verify(IEntityManager).remove(entity);
        Mockito.verifyNoMoreInteractions(IEntityManager, entity, buttonActions);
    }

    @Test
    void testCollisionJumpContactBegin() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.CollisionJumpContactBegin, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).jumpContactBegin();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }

    @Test
    void testCollisionJumpContactEnd() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.CollisionJumpContactEnd, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).jumpContactEnd();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }

    @Test
    void testLadderContactBegin() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.LadderContactBegin, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).ladderContactBegin();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }

    @Test
    void testLadderContactEnd() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.LadderContactEnd, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).ladderContactEnd();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }

    @Test
    void testTrampoline() {
        var levelManager = new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputController, 0, backgroundRenderer, backgroundViewport);
        List<LevelContactListener.Event> events = new ArrayList<>();
        events.add(new LevelContactListener.Event(LevelContactListener.Event.Type.Trampoline, null));
        Mockito.when(contactListener.getEvents()).thenReturn(events);

        levelManager.step();

        Mockito.verify(IEntityManager).update();
        Mockito.verify(IEntityManager).render(Mockito.any());
        Mockito.verify(player).getPosition();
        Mockito.verify(player).trampolineContact();
        Mockito.verifyNoMoreInteractions(IEntityManager, player);
    }
}
