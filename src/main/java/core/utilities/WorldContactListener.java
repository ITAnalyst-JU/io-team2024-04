package core.utilities;

import com.badlogic.gdx.physics.box2d.*;

import core.entities.AbstractEnemy;
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
        if (fixA.getUserData() instanceof Player) {
            playerContact(fixA, fixB);
        }
        if (fixB.getUserData() instanceof Player) {
            playerContact(fixB, fixA);
        }
        if (fixA.getUserData() instanceof AbstractEnemy) {
            enemyContact(fixA, fixB);
        }
        if (fixB.getUserData() instanceof AbstractEnemy) {
            enemyContact(fixB, fixA);
        }
    }

    private void playerContact(Fixture playerFix, Fixture fix2) {
        if (Constants.LayerNames.Deadly.equals(fix2.getUserData()) || fix2.getUserData() instanceof AbstractEnemy) {
            playerDead = true;
            return;
        }
        if (Constants.LayerNames.Finishing.equals(fix2.getUserData())) {
            gameEnded = true;
            return;
        }
    }

    private void enemyContact(Fixture enemyFix, Fixture fix2) {
        if (enemyFix.getUserData() instanceof AbstractEnemy enemy) {
            if (Constants.LayerNames.Deadly.equals(fix2.getUserData())) {
                enemy.setToRemove();
                return;
            }
        }
        else {
            throw new IllegalArgumentException("Not an enemy fixture");
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
