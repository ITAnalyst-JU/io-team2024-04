package core.views;

import com.badlogic.gdx.Screen;

// NOTE: strategy design pattern
public interface IScreenOrchestrator {
    public Screen getScreen(ScreenEnum screenEnum);
    public void notifyOrchestrator(ScreenEnum screenEnum);
}
