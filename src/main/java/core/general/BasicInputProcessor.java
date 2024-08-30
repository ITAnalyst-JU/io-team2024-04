package core.general;

import com.badlogic.gdx.InputProcessor;

// Java will do anything to avoid multiple inheritance.
// There exists Gdx.InputAdapter which is identical, but that is class, not an interface with defaults.
// So here we are.
public interface BasicInputProcessor extends InputProcessor {
    @Override
    default boolean keyDown(int i) {
        return false;
    }

    @Override
    default boolean keyUp(int i){
        return false;
    }

    @Override
    default boolean keyTyped(char c){
        return false;
    }

    @Override
    default boolean touchDown(int i, int i1, int i2, int i3){
        return false;
    }

    @Override
    default boolean touchUp(int i, int i1, int i2, int i3){
        return false;
    }

    @Override
    default boolean touchCancelled(int i, int i1, int i2, int i3){
        return false;
    }

    @Override
    default boolean touchDragged(int i, int i1, int i2){
        return false;
    }

    @Override
    default boolean mouseMoved(int i, int i1){
        return false;
    }

    @Override
    default boolean scrolled(float v, float v1){
        return false;
    }
}
