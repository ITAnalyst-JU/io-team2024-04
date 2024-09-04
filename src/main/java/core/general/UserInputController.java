package core.general;

import com.badlogic.gdx.Input;

public class UserInputController extends Observable<Observer<UserControlsEnum>> implements IInputController {
    @Override
    public boolean keyDown(int i) {
        UserControlsEnum action;
        switch (i) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                action = UserControlsEnum.A_down;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                action = UserControlsEnum.D_down;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                action = UserControlsEnum.W_down;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                action = UserControlsEnum.S_down;
                break;
            case Input.Keys.ESCAPE:
                action = UserControlsEnum.Pause;
                break;
            default:
                return false;
        }
        this.notifyObservers(observer -> observer.respondToEvent(action));
        return true;
    }

    @Override
    public boolean keyUp(int i){
        //make it the same, but with up
        UserControlsEnum action;
        switch (i) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                action = UserControlsEnum.A_up;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                action = UserControlsEnum.D_up;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                action = UserControlsEnum.W_up;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                action = UserControlsEnum.S_up;
                break;
            default:
                return false;
        }
        this.notifyObservers(observer -> observer.respondToEvent(action));
        return true;
    }
}
