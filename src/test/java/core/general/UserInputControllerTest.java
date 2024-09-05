package core.general;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class UserInputControllerTest {

    @Test
    void testWDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.W);
        Mockito.verify(object).respondToEvent(UserControlsEnum.W_down);
    }

    @Test
    void testUpDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.UP);
        Mockito.verify(object).respondToEvent(UserControlsEnum.W_down);
    }

    @Test
    void testADown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.A);
        Mockito.verify(object).respondToEvent(UserControlsEnum.A_down);
    }

    @Test
    void testLeftDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.LEFT);
        Mockito.verify(object).respondToEvent(UserControlsEnum.A_down);
    }

    @Test
    void testSDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.S);
        Mockito.verify(object).respondToEvent(UserControlsEnum.S_down);
    }

    @Test
    void testDownDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.DOWN);
        Mockito.verify(object).respondToEvent(UserControlsEnum.S_down);
    }

    @Test
    void testDDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.D);
        Mockito.verify(object).respondToEvent(UserControlsEnum.D_down);
    }

    @Test
    void testRightDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.RIGHT);
        Mockito.verify(object).respondToEvent(UserControlsEnum.D_down);
    }

    @Test
    void testEscDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.ESCAPE);
        Mockito.verify(object).respondToEvent(UserControlsEnum.Pause);
    }

    @Test
    void testDefaultDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        boolean res = controller.keyDown(Input.Keys.L);
        Mockito.verifyNoInteractions(object);
        Assertions.assertFalse(res);
    }

    @Test
    void testWUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.W);
        Mockito.verify(object).respondToEvent(UserControlsEnum.W_up);
    }

    @Test
    void testUpUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.UP);
        Mockito.verify(object).respondToEvent(UserControlsEnum.W_up);
    }

    @Test
    void testAUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.A);
        Mockito.verify(object).respondToEvent(UserControlsEnum.A_up);
    }

    @Test
    void testLeftUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.LEFT);
        Mockito.verify(object).respondToEvent(UserControlsEnum.A_up);
    }

    @Test
    void testSUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.S);
        Mockito.verify(object).respondToEvent(UserControlsEnum.S_up);
    }

    @Test
    void testDownUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.DOWN);
        Mockito.verify(object).respondToEvent(UserControlsEnum.S_up);
    }

    @Test
    void testDUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.D);
        Mockito.verify(object).respondToEvent(UserControlsEnum.D_up);
    }

    @Test
    void testRightUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.RIGHT);
        Mockito.verify(object).respondToEvent(UserControlsEnum.D_up);
    }

    @Test
    void testDefaultUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        boolean res = controller.keyUp(Input.Keys.L);
        Mockito.verifyNoInteractions(object);
        Assertions.assertFalse(res);
    }
}
