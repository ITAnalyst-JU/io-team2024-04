package core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import core.general.Constants;
import core.general.UserControlsEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerTest {
    IEntity entity;
    Body body;
    Player player;

    @BeforeEach
    void setUp() {
        entity = Mockito.mock(IEntity.class);
        body = Mockito.mock(Body.class);
        Mockito.when(entity.getBody()).thenReturn(body);
        Mockito.when(body.getLinearVelocity()).thenReturn(new Vector2(10, 10));
        player = new Player(entity);
    }

    @Test
    void testConstructor() {
        Mockito.verify(body).setType(BodyDef.BodyType.DynamicBody);
    }

    @Test
    void testGetType() {
        Assertions.assertThat(player.getType()).isEqualTo("player");
    }

    @Test
    void testJumpContactBegin() { // Also has to test responding to events
        player.jumpContactBegin();
        int tries = 10;

        for (int i = 0; i < tries; i++)
            player.respondToEvent(UserControlsEnum.W_down);

        Mockito.verify(body, Mockito.times(tries)).setLinearVelocity(Mockito.anyFloat(), Mockito.eq(Constants.Physics.PlayerJumpSpeed));
    }

    @Test
    void testJumpContactEnd() {
        Mockito.clearInvocations(body);
        player.jumpContactEnd();

        player.respondToEvent(UserControlsEnum.W_down);
        player.respondToEvent(UserControlsEnum.W_down);

        Mockito.verify(body).setLinearVelocity(Mockito.anyFloat(), Mockito.eq(Constants.Physics.PlayerJumpSpeed));
        Mockito.verify(body).getLinearVelocity();
        Mockito.verifyNoMoreInteractions(body);
    }

    @Test
    void testLadderMovementUp() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.W_down);
        player.update();

        Mockito.verify(body).setGravityScale(0f);
        Mockito.verify(body).setLinearVelocity(new Vector2(0f, Constants.Physics.PlayerJumpSpeed / 4f));
    }

    @Test
    void testLadderMovementDown() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.S_down);
        player.update();

        Mockito.verify(body).setGravityScale(0f);
        Mockito.verify(body).setLinearVelocity(new Vector2(0f, -Constants.Physics.PlayerJumpSpeed / 4f));
    }

    @Test
    void testLadderMovementUpEnd() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.W_down);
        player.respondToEvent(UserControlsEnum.W_up);
        player.update();

        Mockito.verify(body).setGravityScale(0f);
        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 0f));
    }

    @Test
    void testLadderMovementDownEnd() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.S_down);
        player.respondToEvent(UserControlsEnum.S_up);
        player.update();

        Mockito.verify(body).setGravityScale(0f);
        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 0f));
    }

    @Test
    void testLadderContactEnd() {
        player.ladderContactEnd();
        player.update();

        Mockito.verify(body).setGravityScale(1f);
        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testLadderContactBeginEnd() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.W_down);
        player.ladderContactEnd();
        player.ladderContactBegin();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, Constants.Physics.PlayerJumpSpeed / 4f));
    }

    @Test
    void testLadderContactMoveThenBegin() {
        player.respondToEvent(UserControlsEnum.W_down);
        player.ladderContactBegin();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, Constants.Physics.PlayerJumpSpeed / 4f));
    }

    @Test
    void testReverseGravity() {
        Mockito.when(body.getGravityScale()).thenReturn(-17.5f);

        player.reverseGravity();

        Mockito.verify(body).getGravityScale();
        Mockito.verify(body).setGravityScale(17.5f);
    }

    @Test
    void testTrampolineContact() {
        player.trampolineContact();
        Mockito.verify(body).getLinearVelocity();
        Mockito.verify(body).setLinearVelocity(new Vector2(10f, 2 * Constants.Physics.PlayerJumpSpeed));
    }

    @Test
    void testHideThrowsException() {
        Assertions.assertThatThrownBy(() -> player.hide()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testDisposeThrowsException() {
        Assertions.assertThatThrownBy(() -> player.dispose()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testMoveLeft() {
        player.respondToEvent(UserControlsEnum.A_down);
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(-Constants.Physics.PlayerMoveSpeed, 10f));
    }

    @Test
    void testMoveRight() {
        player.respondToEvent(UserControlsEnum.D_down);
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(Constants.Physics.PlayerMoveSpeed, 10f));
    }

    @Test
    void testMoveLeftRight() {
        player.respondToEvent(UserControlsEnum.A_down);
        player.respondToEvent(UserControlsEnum.D_down);
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testMoveBeginEndLeft() {
        player.respondToEvent(UserControlsEnum.A_down);
        player.respondToEvent(UserControlsEnum.A_up);
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testMoveBeginEndRight() {
        player.respondToEvent(UserControlsEnum.D_down);
        player.respondToEvent(UserControlsEnum.D_up);
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testResetControlsLeft() {
        player.respondToEvent(UserControlsEnum.A_down);
        player.resetControls();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testResetControlsRight() {
        player.respondToEvent(UserControlsEnum.D_down);
        player.resetControls();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 10f));
    }

    @Test
    void testResetControlsUp() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.W_down);
        player.resetControls();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 0));
    }

    @Test
    void testResetControlsDown() {
        player.ladderContactBegin();
        player.respondToEvent(UserControlsEnum.S_down);
        player.resetControls();
        player.update();

        Mockito.verify(body).setLinearVelocity(new Vector2(0f, 0));
    }
}