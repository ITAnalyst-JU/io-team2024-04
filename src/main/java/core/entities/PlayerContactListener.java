package core.entities;

public interface PlayerContactListener {
    boolean playerLegalJump();

    // Potentially other collisions which affect player.
    // However, how many would actually be managed by Player class, not by level?
}
