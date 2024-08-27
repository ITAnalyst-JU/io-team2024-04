package core.entities;

import com.badlogic.gdx.physics.box2d.*;

import core.utilities.Constants;

public class LevelContactListener implements ContactListener, PlayerContactListener {

    private boolean gameEnded;
    private boolean playerDead;
    private boolean checkpointReached;

    private boolean playerContactWithTiles;
    private boolean playerMidairJumpLeft;

    public LevelContactListener(){
        gameEnded = false;
        playerDead = false;
        playerContactWithTiles = false;
        playerMidairJumpLeft = false;
        checkpointReached = false;
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
        if (Constants.LayerNames.Finishing.equals(fix2.getUserData())) {
            gameEnded = true;
            return;
        }
        if (Constants.LayerNames.Deadly.equals(fix2.getUserData()) || fix2.getUserData() instanceof Enemy) {
            playerDead = true;
        }
        if (Constants.LayerNames.Collision.equals(fix2.getUserData())
                || fix2.getUserData() instanceof Platform) {
            playerContactWithTiles = true;
        }

        if (! (fix2.getUserData() instanceof BodyEntity)) {
            return;
        }
        BodyEntity fix2Body = (BodyEntity) fix2.getUserData();
        if (Constants.LayerNames.Checkpoint.equals(fix2Body.getType())) {
            checkpointReached = true;
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

    public boolean isCheckpointReached() {
        return this.checkpointReached;
    }

    public void setCheckpointReached(boolean checkpointReached) {
        this.checkpointReached = checkpointReached;
    }
}
