package core.utilities;

import com.badlogic.gdx.physics.box2d.*;
import core.entities.Player;

public class WorldContactListener implements ContactListener {

    private boolean gameEnded;
    private boolean playerDead;

    public WorldContactListener() {

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
        if (Constants.LayerNames.Finishing.equals(fixB.getUserData())) {
            gameEnded = true;
            return;
        }
        if (Constants.LayerNames.Deadly.equals(fixB.getUserData())) {
            playerDead = true;
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

    public boolean isGameEnded() {
        return this.gameEnded;
    }

    public boolean isPlayerDead() {
        return this.playerDead;
    }

    public void setPlayerDead(boolean playerDead) {
        this.playerDead = playerDead;
    }
}
