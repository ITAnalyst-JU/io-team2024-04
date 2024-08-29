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
        }
        Type type;
        Object object;

        Event(Type type, Object object) {
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
        if (Constants.LayerNames.Deadly.equals(fix2.getUserData()) || fix2.getUserData() instanceof Enemy) {
            events.add(new Event(Event.Type.Death, null));
        }
        if (Constants.LayerNames.Collision.equals(fix2.getUserData())
                || fix2.getUserData() instanceof Platform) {
            events.add(new Event(Event.Type.CollisionJumpContactBegin, null));
        }

        if (! (fix2.getUserData() instanceof BodyEntity)) {
            return;
        }
        BodyEntity fix2Body = (BodyEntity) fix2.getUserData();
        if (Constants.LayerNames.Finishing.equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.Finish, null));
        }
        if (Constants.LayerNames.Checkpoint.equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.Checkpoint, fix2Body));
        }
        if ("ladder".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.LadderContactBegin, null));
        }
        if ("gravity".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.GravityReverse, null));
        }
        if ("platform".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.Platform, fix2Body));
        }
        if ("button".equals(fix2Body.getType())) {
            events.add(new Event(Event.Type.Button, fix2Body));
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
        if (Constants.LayerNames.Collision.equals(fix2.getUserData())
                || fix2.getUserData() instanceof Platform) {
            events.add(new Event(Event.Type.CollisionJumpContactEnd, null));
        }

        if (! (fix2.getUserData() instanceof BodyEntity)) {
            return;
        }
        BodyEntity fix2Body = (BodyEntity) fix2.getUserData();
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
