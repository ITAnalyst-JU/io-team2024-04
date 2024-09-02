package core.levels;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import core.entities.IEntity;
import core.entities.Player;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class LevelContactListenerTest {
    private List<LevelContactListener.Event> events;
    LevelContactListener contactListener;
    Contact contact;
    Fixture fixture1;
    Fixture fixture2;
    Object entity1;
    Object entity2;

    @BeforeEach
    void setUp() {
        contactListener = new LevelContactListener();
        contact = Mockito.mock(Contact.class);
        fixture1 = Mockito.mock(Fixture.class);
        fixture2 = Mockito.mock(Fixture.class);
        entity1 = Mockito.mock(Player.class);
        entity2 = Mockito.mock(IEntity.class);
        Mockito.when(contact.getFixtureA()).thenReturn(fixture1);
        Mockito.when(contact.getFixtureB()).thenReturn(fixture2);
        Mockito.when(fixture1.getUserData()).thenReturn(entity1);
        Mockito.when(fixture2.getUserData()).thenReturn(entity2);
    }

    @Test
    void testEmptyList() {
        events = contactListener.getEvents();
        Assertions.assertThat(events.size()).isEqualTo(0);
    }

    @Test
    void testDeadlyLayer() {
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Deadly);

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Death);
    }

    @Test
    void testEnemy() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("enemy");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Death);
    }

    @Test
    void testCollisionJumpLayerBegin() {
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Collision);

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactBegin);
    }

    @Test
    void testCollisionNoJumpLayerBegin() {
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.CollisionNoJump);

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(0);
    }

    @Test
    void testPlatformJumpBegin() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("platform");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactBegin);
    }

    @Test
    void testCollisionJumpLayerEnd() {
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Collision);

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactEnd);
    }

    @Test
    void testCollisionNoJumpLayerEnd() {
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.CollisionNoJump);

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(0);
    }

    @Test
    void testPlatformJumpEnd() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("platform");

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactEnd);
    }

    @Test
    void testFinish() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Finishing);

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Finish);
    }

    @Test
    void testCheckpoint() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Checkpoint);

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Checkpoint);
        Assertions.assertThat(events.getFirst().object).isEqualTo(entity2);
    }

    @Test
    void testPlatformObjectBegin() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("platform");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(2);
        Assertions.assertThat(events.get(1).type).isEqualTo(LevelContactListener.Event.Type.Platform);
        Assertions.assertThat(events.get(1).object).isEqualTo(entity2);
    }

    @Test
    void testPlatformObjectEnd() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("platform");

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(2);
        Assertions.assertThat(events.get(1).type).isEqualTo(LevelContactListener.Event.Type.Platform);
        Assertions.assertThat(events.get(1).object).isEqualTo(entity2);
    }

    @Test
    void testLadderBegin() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("ladder");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.LadderContactBegin);
    }

    @Test
    void testLadderEnd() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("ladder");

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.LadderContactEnd);
    }

    @Test
    void testGravity() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("gravity");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.GravityReverse);
    }

    @Test
    void testButton() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("button");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Button);
        Assertions.assertThat(events.getFirst().object).isEqualTo(entity2);
    }

    @Test
    void testTrampoline() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("trampoline");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Trampoline);
    }

    // Theoretically, we could test everything reversed, but come on...

    @Test
    void testReversedOrderButton() {
        Mockito.when(fixture1.getUserData()).thenReturn(entity2);
        Mockito.when(fixture2.getUserData()).thenReturn(entity1);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("button");

        contactListener.beginContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.Button);
        Assertions.assertThat(events.getFirst().object).isEqualTo(entity2);
    }

    @Test
    void testReversedOrderLadderEnd() {
        Mockito.when(fixture1.getUserData()).thenReturn(entity2);
        Mockito.when(fixture2.getUserData()).thenReturn(entity1);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("ladder");

        contactListener.endContact(contact);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(1);
        Assertions.assertThat(events.getFirst().type).isEqualTo(LevelContactListener.Event.Type.LadderContactEnd);
    }

    @Test
    void testMultipleInteractionsBegin() {
        Mockito.when(((IEntity)entity2).getType()).thenReturn("button");
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("trampoline");
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("gravity");
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("ladder");
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("platform");
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("enemy");
        contactListener.beginContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Deadly);
        contactListener.beginContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(entity2);
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Finishing);
        contactListener.beginContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Checkpoint);
        contactListener.beginContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Collision);
        contactListener.beginContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.CollisionNoJump);
        contactListener.beginContact(contact);

        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(11);
        Assertions.assertThat(events.get(0).type).isEqualTo(LevelContactListener.Event.Type.Button);
        Assertions.assertThat(events.get(1).type).isEqualTo(LevelContactListener.Event.Type.Trampoline);
        Assertions.assertThat(events.get(2).type).isEqualTo(LevelContactListener.Event.Type.GravityReverse);
        Assertions.assertThat(events.get(3).type).isEqualTo(LevelContactListener.Event.Type.LadderContactBegin);
        Assertions.assertThat(events.get(4).type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactBegin);
        Assertions.assertThat(events.get(5).type).isEqualTo(LevelContactListener.Event.Type.Platform);
        Assertions.assertThat(events.get(6).type).isEqualTo(LevelContactListener.Event.Type.Death);
        Assertions.assertThat(events.get(7).type).isEqualTo(LevelContactListener.Event.Type.Death);
        Assertions.assertThat(events.get(8).type).isEqualTo(LevelContactListener.Event.Type.Finish);
        Assertions.assertThat(events.get(9).type).isEqualTo(LevelContactListener.Event.Type.Checkpoint);
        Assertions.assertThat(events.get(10).type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactBegin);
    }

    @Test
    void testMultipleInteractionsEnd() {
        Mockito.when(((IEntity) entity2).getType()).thenReturn("ladder");
        contactListener.endContact(contact);
        Mockito.when(((IEntity) entity2).getType()).thenReturn("platform");
        contactListener.endContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Collision);
        contactListener.endContact(contact);

        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(4);
        Assertions.assertThat(events.get(0).type).isEqualTo(LevelContactListener.Event.Type.LadderContactEnd);
        Assertions.assertThat(events.get(1).type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactEnd);
        Assertions.assertThat(events.get(2).type).isEqualTo(LevelContactListener.Event.Type.Platform);
        Assertions.assertThat(events.get(3).type).isEqualTo(LevelContactListener.Event.Type.CollisionJumpContactEnd);
    }

    @Test
    void testContactEndNoInteractions() {

        Mockito.when(((IEntity)entity2).getType()).thenReturn("button");
        contactListener.endContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("trampoline");
        contactListener.endContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("gravity");
        contactListener.endContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn("enemy");
        contactListener.endContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.Deadly);
        contactListener.endContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(entity2);
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Finishing);
        contactListener.endContact(contact);
        Mockito.when(((IEntity)entity2).getType()).thenReturn(Constants.LayerNames.Checkpoint);
        contactListener.endContact(contact);
        Mockito.when(fixture2.getUserData()).thenReturn(Constants.LayerNames.CollisionNoJump);
        contactListener.endContact(contact);

        events = contactListener.getEvents();


        Assertions.assertThat(events.size()).isEqualTo(0);
    }

    @Test
    void testPreSolveDoesNothing() {
        Contact contact = Mockito.mock(Contact.class);
        Manifold manifold = Mockito.mock(Manifold.class);

        contactListener.preSolve(contact, manifold);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(0);
        Mockito.verifyNoInteractions(contact, manifold);
    }

    @Test
    void testPostSolveDoesNothing() {
        Contact contact = Mockito.mock(Contact.class);
        ContactImpulse contactImpulse = Mockito.mock(ContactImpulse.class);

        contactListener.postSolve(contact, contactImpulse);
        events = contactListener.getEvents();

        Assertions.assertThat(events.size()).isEqualTo(0);
        Mockito.verifyNoInteractions(contact, contactImpulse);
    }

}