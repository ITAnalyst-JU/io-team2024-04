package core.levels;

import core.general.UserInputController;

public interface ILevelManager {
    void create();

    void resume();

    void pause();

    // Untestable. Dependancies on untestable libgdx parts, like camera.position .
    void step();

    void dispose();

    void resize(int i, int i1);

    long getTimePassed();

    boolean isGameEnded();

    UserInputController getInputController();

    int getLevelNumber();
}
