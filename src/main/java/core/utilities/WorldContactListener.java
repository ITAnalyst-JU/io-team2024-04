package core.utilities;

import com.badlogic.gdx.physics.box2d.*;

import core.entities.AbstractEnemy;
import core.entities.AbstractPlatform;
import core.entities.Player;
import core.entities.PlayerContactListener;

public class WorldContactListener implements ContactListener, PlayerContactListener {

    private boolean gameEnded;
    private boolean playerDead;

    private boolean playerContactWithTiles;
    private boolean playerMidairJumpLeft;

    public WorldContactListener(){
        gameEnded = false;
        playerDead = false;
        playerContactWithTiles = false;
        playerMidairJumpLeft = false;
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
        if (fixA.getUserData() instanceof AbstractEnemy) {
            enemyContact(fixA, fixB);
        }
        if (fixB.getUserData() instanceof AbstractEnemy) {
            enemyContact(fixB, fixA);
        }
    }

    private void playerContactBegin(Fixture playerFix, Fixture fix2) {
        if (Constants.LayerNames.Finishing.equals(fix2.getUserData())) {
            gameEnded = true;
            return;
        }
        if (Constants.LayerNames.Deadly.equals(fix2.getUserData()) || fix2.getUserData() instanceof AbstractEnemy) {
            playerDead = true;
        }
        if (Constants.LayerNames.Collision.equals(fix2.getUserData())
                || fix2.getUserData() instanceof AbstractPlatform) {
            playerContactWithTiles = true;
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
                || fix2.getUserData() instanceof AbstractPlatform) {
            playerContactWithTiles = false;
            playerMidairJumpLeft = true;
        }
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

    @Override
    public boolean playerLegalJump(){
        if (this.playerContactWithTiles) {
            return true;
        }
        if (this.playerMidairJumpLeft) {
            this.playerMidairJumpLeft = false;
            return true;
        }
        return false;
    }
}
