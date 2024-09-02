package core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import core.assets.IAssetManagerGetter;
import core.entities.decorators.DecoratorFactory;
import core.entities.decorators.MovingDecorator;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class EntityFactoryTest {
    EntityFactory factory;
    Vector2 baseEntitySize;
    World world;
    IAssetManagerGetter assetManager;
    Body body;
    RectangleMapObject mapObject;
    Rectangle rectangle;
    Array<Fixture> fixtureList;
    Fixture fixture;
    DecoratorFactory decoratorFactory;
    MapProperties properties;

    @BeforeEach
    void setUp() {
        baseEntitySize = new Vector2(2, 2);
        world = Mockito.mock(World.class);
        assetManager = Mockito.mock(IAssetManagerGetter.class);
        body = Mockito.mock(Body.class);
        mapObject = Mockito.mock(RectangleMapObject.class);
        rectangle = Mockito.mock(Rectangle.class);
        properties = Mockito.mock(MapProperties.class);
        fixtureList = new Array<>();
        fixture = Mockito.mock(Fixture.class);
        fixtureList.add(fixture);
        decoratorFactory = Mockito.spy(new DecoratorFactory());

        Mockito.when(assetManager.getTexture(Mockito.anyString())).thenReturn(Mockito.mock(Texture.class));
        Mockito.when(world.createBody(Mockito.any())).thenReturn(body);
        Mockito.when(mapObject.getRectangle()).thenReturn(rectangle);
        Mockito.when(mapObject.getProperties()).thenReturn(properties);
        Mockito.when(rectangle.getPosition(Mockito.any())).thenReturn(new Vector2(10, 10));
        Mockito.when(rectangle.getWidth()).thenReturn(2f);
        Mockito.when(rectangle.getHeight()).thenReturn(2f);
        Mockito.when(body.getFixtureList()).thenReturn(fixtureList);
        Mockito.when(body.createFixture(Mockito.any())).thenReturn(fixture);

        factory = new EntityFactory(baseEntitySize, world, assetManager, decoratorFactory);
    }

    @Test
    void testGetPlayerThrowsWhenNotRectangle() {
        MapObject object = Mockito.mock(MapObject.class);
        Assertions.assertThatThrownBy(() -> factory.getPlayer(object)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testGetPlayer() {
        Player player = factory.getPlayer(mapObject);

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(player);
        Assertions.assertThat(player).isInstanceOf(IEntity.class);

        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
    }

    @Test
    void testGetEntityThrowsWhenNotRectangle() {
        MapObject object = Mockito.mock(MapObject.class);
        Assertions.assertThatThrownBy(() -> factory.getEntity(object, false)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testGetBodyOnlyGenericEntity() {
        Mockito.when(properties.get("type")).thenReturn("testType");
        IEntity entity = factory.getEntity(mapObject, false);

        var type = entity.getType();

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(entity);
        Assertions.assertThat(type).isEqualTo("testType");
    }

    @Test
    void testGetButton() {
        Mockito.when(properties.get("type")).thenReturn("button");
        Mockito.when(properties.get("number")).thenReturn(5);
        IEntity entity = factory.getEntity(mapObject, false);

        var type = entity.getType();
        var num = entity.getButtonNumber();

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(entity);
        Assertions.assertThat(type).isEqualTo("button");
        Assertions.assertThat(num).isEqualTo(5);
    }

    @Test
    void testGetSimpleStaticPlatform() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(entity);
        Assertions.assertThat(type).isEqualTo("platform");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetHorizontalPlatform() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(10f);
        Mockito.when(rectangle.getX()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        IEntity entity = factory.getEntity(mapObject, true);


        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.HORIZONTAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetVerticalPlatform() {
        Mockito.when(rectangle.getHeight()).thenReturn(10f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(rectangle.getY()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        IEntity entity = factory.getEntity(mapObject, true);

        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.VERTICAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetPlatformWithLife() {
        Mockito.when(rectangle.getHeight()).thenReturn(10f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(rectangle.getY()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        int life = 1;
        Mockito.when(properties.get("lives")).thenReturn(life);
        Mockito.when(properties.containsKey("lives")).thenReturn(true);

        IEntity entity = factory.getEntity(mapObject, true);
        var res = entity.damage();

        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.VERTICAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verify(decoratorFactory).getDamageableDecorator(Mockito.any(), Mockito.eq(1));
        Mockito.verify(decoratorFactory).getUserDamageableDecorator(Mockito.any());
        Mockito.verifyNoMoreInteractions(decoratorFactory);
        Assertions.assertThat(res).isTrue();
    }

    @Test
    void testGetPlatformWithLifeUserDamageable() {
        Mockito.when(rectangle.getHeight()).thenReturn(10f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(rectangle.getY()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        int life = 1;
        Mockito.when(properties.get("lives")).thenReturn(life);
        Mockito.when(properties.containsKey("lives")).thenReturn(true);
        Mockito.when(properties.containsKey("isUserDamageable")).thenReturn(true);
        Mockito.when(properties.get("isUserDamageable")).thenReturn(true);

        IEntity entity = factory.getEntity(mapObject, true);
        var res = entity.getUserDamageable();
        var res2 = entity.damage();

        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.VERTICAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verify(decoratorFactory).getDamageableDecorator(Mockito.any(), Mockito.eq(1));
        Mockito.verify(decoratorFactory).getUserDamageableDecorator(Mockito.any());
        Mockito.verifyNoMoreInteractions(decoratorFactory);
        Assertions.assertThat(res).isTrue();
        Assertions.assertThat(res2).isTrue();
    }

    @Test
    void testGetPlatformWithLifeNotUserDamageable() {
        Mockito.when(rectangle.getHeight()).thenReturn(10f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(rectangle.getY()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        int life = 1;
        Mockito.when(properties.get("lives")).thenReturn(life);
        Mockito.when(properties.containsKey("lives")).thenReturn(true);
        Mockito.when(properties.containsKey("isUserDamageable")).thenReturn(true);
        Mockito.when(properties.get("isUserDamageable")).thenReturn(false);

        IEntity entity = factory.getEntity(mapObject, true);
        var res = entity.getUserDamageable();
        var res2 = entity.damage();

        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.VERTICAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verify(decoratorFactory).getDamageableDecorator(Mockito.any(), Mockito.eq(1));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
        Assertions.assertThat(res).isFalse();
        Assertions.assertThat(res2).isTrue();
    }

    @Test
    void testGetWidePlatform() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        int width = 3;
        Mockito.when(properties.get("width")).thenReturn(width);
        IEntity entity = factory.getEntity(mapObject, true);
        baseEntitySize.x *= width;

        var type = entity.getType();

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(entity);
        Assertions.assertThat(type).isEqualTo("platform");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetEnemyStatic() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("enemy");
        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();

        Assertions.assertThat(type).isEqualTo("enemy");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("enemy"));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetEnemyMovingHorizontal() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(10f);
        Mockito.when(rectangle.getY()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("enemy");
        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();

        ArgumentCaptor<Object> userDataCaptor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(fixture, Mockito.times(2)).setUserData(userDataCaptor.capture());
        Assertions.assertThat(userDataCaptor.getAllValues().get(1)).isSameAs(entity);
        Assertions.assertThat(type).isEqualTo("enemy");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("enemy"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.HORIZONTAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetEnemyMovingVertical() {
        Mockito.when(rectangle.getHeight()).thenReturn(10f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(rectangle.getX()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("enemy");
        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();

        Assertions.assertThat(type).isEqualTo("enemy");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("enemy"));
        Mockito.verify(decoratorFactory).getMovingDecorator(Mockito.any(), Mockito.eq(MovingDecorator.MovementDirection.VERTICAL), Mockito.eq(new Vector2(1f / Constants.Physics.Scale, 9f / Constants.Physics.Scale)));
        Mockito.verifyNoMoreInteractions(decoratorFactory);
    }

    @Test
    void testGetButtonActionDamagePlatform() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("platform");
        Mockito.when(properties.containsKey("number")).thenReturn(true);
        Mockito.when(properties.get("number")).thenReturn(5);
        int life = 1;
        Mockito.when(properties.get("lives")).thenReturn(life);
        Mockito.when(properties.containsKey("lives")).thenReturn(true);
        Mockito.when(properties.containsKey("isUserDamageable")).thenReturn(true);
        Mockito.when(properties.get("isUserDamageable")).thenReturn(false);

        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();
        var isUserDamageable = entity.getUserDamageable();
        var ifDead = entity.buttonAction(entity);
        var buttonActionsMap = factory.getButtonsMap();

        Assertions.assertThat(type).isEqualTo("platform");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("platform"));
        Mockito.verify(decoratorFactory).getDamageableDecorator(Mockito.any(), Mockito.eq(life));
        Mockito.verify(decoratorFactory).getButtonActionDamageDecorator(Mockito.any());
        Mockito.verifyNoMoreInteractions(decoratorFactory);
        Assertions.assertThat(isUserDamageable).isFalse();
        Assertions.assertThat(ifDead).isTrue();
        Assertions.assertThat(buttonActionsMap.get(5)).isSameAs(entity);
        Assertions.assertThat(buttonActionsMap.size()).isEqualTo(1);
    }

    @Test
    void testGetButtonActionDamageEnemy() {
        Mockito.when(rectangle.getHeight()).thenReturn(0f);
        Mockito.when(rectangle.getWidth()).thenReturn(0f);
        Mockito.when(properties.get("type")).thenReturn("enemy");
        Mockito.when(properties.containsKey("number")).thenReturn(true);
        Mockito.when(properties.get("number")).thenReturn(5);
        int life = 1;
        Mockito.when(properties.get("lives")).thenReturn(life);
        Mockito.when(properties.containsKey("lives")).thenReturn(true);
        Mockito.when(properties.containsKey("isUserDamageable")).thenReturn(true);
        Mockito.when(properties.get("isUserDamageable")).thenReturn(false);

        IEntity entity = factory.getEntity(mapObject, true);

        var type = entity.getType();
        var isUserDamageable = entity.getUserDamageable();
        var ifDead = entity.buttonAction(entity);
        var buttonActionsMap = factory.getButtonsMap();

        Assertions.assertThat(type).isEqualTo("enemy");
        Mockito.verify(decoratorFactory).getSpriteDecorator(Mockito.any(), Mockito.any(), Mockito.eq(baseEntitySize));
        Mockito.verify(decoratorFactory).getGenericTypeDecorator(Mockito.any(), Mockito.eq("enemy"));
        Mockito.verify(decoratorFactory).getDamageableDecorator(Mockito.any(), Mockito.eq(life));
        Mockito.verify(decoratorFactory).getButtonActionDamageDecorator(Mockito.any());
        Mockito.verifyNoMoreInteractions(decoratorFactory);
        Assertions.assertThat(isUserDamageable).isFalse();
        Assertions.assertThat(ifDead).isTrue();
        Assertions.assertThat(buttonActionsMap.get(5)).isSameAs(entity);
        Assertions.assertThat(buttonActionsMap.size()).isEqualTo(1);
    }

    @Test
    void testUnknownVisibleEntity() {
        Mockito.when(properties.get("type")).thenReturn("testType");

        Assertions.assertThatThrownBy(() -> factory.getEntity(mapObject, true)).isInstanceOf(UnsupportedOperationException.class);
    }
}