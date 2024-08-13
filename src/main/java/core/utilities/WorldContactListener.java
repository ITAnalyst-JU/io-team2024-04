package core.utilities;

import com.badlogic.gdx.physics.box2d.*;
import core.entities.Player;
import core.views.LevelInterface;

public class WorldContactListener implements ContactListener {
    private LevelInterface level;

    public WorldContactListener(LevelInterface level) {
        this.level = level;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if (fixB.getUserData() instanceof Player) {
            Fixture temp = fixA;
            fixA = fixB;
            fixB = temp;
        }
        System.out.println(fixA.getUserData());
        System.out.println(fixB.getUserData());
        if (Constants.LayerNames.Finishing.equals(fixB.getUserData())) {
            level.setLevelFinished();
            return;
        }
        if (Constants.LayerNames.Deadly.equals(fixB.getUserData())) {
            level.setPlayerDied();
            return;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
