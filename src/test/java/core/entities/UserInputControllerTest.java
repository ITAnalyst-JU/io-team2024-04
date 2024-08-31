package core.entities;

import com.badlogic.gdx.Input;
import core.general.Observer;
import core.general.UserControlsEnum;
import core.general.UserInputController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class UserInputControllerTest {

    @Test
    public void testWDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.W);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.W_down);
    }

    @Test
    public void testADown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.A);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.A_down);
    }

    @Test
    public void testSDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.S);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.S_down);
    }

    @Test
    public void testDDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.D);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.D_down);
    }

    @Test
    public void testEscDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.ESCAPE);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.Pause);
    }

    @Test
    public void testDefaultDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        boolean res = controller.keyDown(Input.Keys.L);
        Mockito.verifyNoInteractions(object);
        Assertions.assertFalse(res);
    }

    @Test
    public void testWUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.W);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.W_up);
    }

    @Test
    public void testAUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.A);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.A_up);
    }

    @Test
    public void testSUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.S);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.S_up);
    }

    @Test
    public void testDUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.D);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.D_up);
    }

    @Test
    public void testDefaultUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        boolean res = controller.keyUp(Input.Keys.L);
        Mockito.verifyNoInteractions(object);
        Assertions.assertFalse(res);
    }
}
