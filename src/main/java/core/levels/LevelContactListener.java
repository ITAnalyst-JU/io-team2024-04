package core.levels;

import com.badlogic.gdx.physics.box2d.*;
import core.entities.*;
import core.general.Constants;

import java.util.ArrayList;
import java.util.List;

public class LevelContactListener implements ContactListener {

    public static class Event {
        public enum Type {
            Finish,
            Checkpoint,
            Platform,
            Death,
            GravityReverse,
            Button,
            CollisionJumpContactBegin,
            CollisionJumpContactEnd,
            LadderContactBegin,
            LadderContactEnd,
            Trampoline,
        }
        Type type;
        IEntity object;

        Event(Type type, IEntity object) {
            this.type = type;
            this.object = object;
        }
    }

    private final List<Event> events;

    public LevelContactListener() {
        events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() instanceof Player) {
            playerContactBegin(fixA, fixB);
        }
        if (fixB.getUserData() instanceof Player) {
            playerContactBegin(fixB, fixA);
        }
    }

    private void playerContactBegin(Fixture playerFix, Fixture fix2) {
        if (Constants.LayerNames.Deadly.equals(fix2.getUserData()) || (fix2.getUserData() instanceof IEntity && ((IEntity)fix2.getUserData()).getType().equals("enemy"))) {
            events.add(new Event(Event.Type.Death, null));
        }
        if (Constants.LayerNames.Collision.equals(fix2.getUserData()) || (fix2.getUserData() instanceof IEntity && ((IEntity)fix2.getUserData()).getType().equals("platform"))) {
            events.add(new Event(Event.Type.CollisionJumpContactBegin, null));
        }

        if (! (fix2.getUserData() instanceof IEntity)) {
            return;
        }
        IEntity entity = (IEntity) fix2.getUserData();
        if (Constants.LayerNames.Finishing.equals(entity.getType())) {
            events.add(new Event(Event.Type.Finish, null));
        }
        if (Constants.LayerNames.Checkpoint.equals(entity.getType())) {
            events.add(new Event(Event.Type.Checkpoint, entity));
        }
        if ("platform".equals(entity.getType())) {
            events.add(new Event(Event.Type.Platform, entity));
        }
        if ("ladder".equals(entity.getType())) {
            events.add(new Event(Event.Type.LadderContactBegin, null));
        }
        if ("gravity".equals(entity.getType())) {
            events.add(new Event(Event.Type.GravityReverse, null));
        }
        if ("button".equals(entity.getType())) {
            events.add(new Event(Event.Type.Button, entity));
        }
        if ("trampoline".equals(entity.getType())) {
            events.add(new Event(Event.Type.Trampoline, null));
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() instanceof Player) {
            playerContactEnd(fixA, fixB);
        }
        if (fixB.getUserData() instanceof Player) {
            playerContactEnd(fixB, fixA);
        }
    }

    private void playerContactEnd(Fixture playerFix, Fixture fix2) {
        if (Constants.LayerNames.Collision.equals(fix2.getUserData()) || (fix2.getUserData() instanceof IEntity && ((IEntity)fix2.getUserData()).getType().equals("platform"))) {
            events.add(new Event(Event.Type.CollisionJumpContactEnd, null));
        }

        if (! (fix2.getUserData() instanceof IEntity)) {
            return;
        }
        IEntity fix2Body = (IEntity) fix2.getUserData();
        if ("platform".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.Platform, fix2Body));
        }
        if ("ladder".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.LadderContactEnd, null));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
