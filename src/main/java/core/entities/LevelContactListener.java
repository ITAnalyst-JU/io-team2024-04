package core.entities;

import com.badlogic.gdx.physics.box2d.*;

import core.utilities.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelContactListener implements ContactListener, PlayerContactListener {

    private boolean gameEnded;
    private boolean playerDead;
    private boolean checkpointReached;

    private boolean playerContactWithTiles;
    private boolean playerMidairJumpLeft;

    public boolean playerLadderContact;
    public boolean playerLadderEvent;

    public boolean revreseGravity = false;

    public int button = 0;

    private final List<BodyEntity> bodiesToRemove = new ArrayList<>();

    public List<BodyEntity> getBodiesToRemove() {
        return Collections.unmodifiableList(bodiesToRemove);
    }

    public void clearBodiesToRemove() {
        bodiesToRemove.clear();
    }

    public LevelContactListener(){
        gameEnded = false;
        playerDead = false;
        playerContactWithTiles = false;
        playerMidairJumpLeft = false;
        checkpointReached = false;
        playerLadderContact = false;
        playerLadderEvent = false;
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
        if (Constants.LayerNames.Finishing.equals(fix2Body.getType())) {
            gameEnded = true;
        }
        if (Constants.LayerNames.Checkpoint.equals(fix2Body.getType())) {
            checkpointReached = true;
            bodiesToRemove.add(fix2Body);
        }
        if ("ladder".equals(fix2Body.getType())) {
            playerLadderContact = true;
            playerLadderEvent = true;
        }
        if ("gravity".equals(fix2Body.getType())) {
            revreseGravity = true;
        }
        if ("platform".equals(fix2Body.getType())) {
            if (((Platform)fix2Body).damage()) {
                bodiesToRemove.add(fix2Body);
            }
        }
        if ("button".equals(fix2Body.getType())) {
            this.button = ((Button)fix2Body).getNumber();
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

        if (! (fix2.getUserData() instanceof BodyEntity)) {
            return;
        }
        BodyEntity fix2Body = (BodyEntity) fix2.getUserData();
        if ("platform".equals(fix2Body.getType())) {
            if (((Platform)fix2Body).damage()) {
                bodiesToRemove.add(fix2Body);
            }
        }
        if ("ladder".equals(fix2Body.getType())) {
            playerLadderContact = false;
            playerLadderEvent = true;
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
