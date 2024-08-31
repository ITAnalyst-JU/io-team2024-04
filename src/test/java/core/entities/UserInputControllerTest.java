package core.entities;

import com.badlogic.gdx.Input;
import core.general.Observer;
import core.general.UserControlsEnum;
import core.general.UserInputController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class UserInputControllerTest {

    @Test
    public void testKeyDown() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyDown(Input.Keys.A);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.A_down);
    }

    @Test
    public void testKeyUp() {
        UserInputController controller = new UserInputController();
        var object = Mockito.mock(Observer.class);
        controller.addObserver((Observer<UserControlsEnum>)object);
        controller.keyUp(Input.Keys.W);
        Mockito.verify(object, Mockito.times(1)).respondToEvent(UserControlsEnum.W_up);
    }
}
