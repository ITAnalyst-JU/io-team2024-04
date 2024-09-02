package core.general;

import com.badlogic.gdx.Input;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class IInputControllerTest {
    // We will be testing default methods
    static class DummyClass implements IInputController {}

    @Test
    public void testKeyDown() {
        var inputController = new DummyClass();

        var res = inputController.keyDown(Input.Keys.S);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testKeyUp() {
        var inputController = new DummyClass();

        var res = inputController.keyUp(Input.Keys.D);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testKeyTyped() {
        var inputController = new DummyClass();

        var res = inputController.keyTyped('a');

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testTouchDown() {
        var inputController = new DummyClass();

        var res = inputController.touchDown(1, 2, 3, 4);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testTouchUp() {
        var inputController = new DummyClass();

        var res = inputController.touchUp(1, 2, 3, 4);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testTouchCancelled() {
        var inputController = new DummyClass();

        var res = inputController.touchCancelled(1, 2, 3, 4);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testTouchDragged() {
        var inputController = new DummyClass();

        var res = inputController.touchDragged(1, 2, 3);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testMouseMoved() {
        var inputController = new DummyClass();

        var res = inputController.mouseMoved(1, 2);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testScrolled() {
        var inputController = new DummyClass();

        var res = inputController.scrolled(1, 2);

        Assertions.assertThat(res).isFalse();
    }
}
