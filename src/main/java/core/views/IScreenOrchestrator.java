package core.views;

import com.badlogic.gdx.Screen;
import core.levels.AbstractLevel;
import core.levels.LevelEnum;

// NOTE: strategy design pattern
public interface IScreenOrchestrator {
    Screen getScreen(ScreenEnum screenEnum);
    LevelEnum getNextLevel();
    ScreenEnum getNextScreenEnum();

    // TODO: consider making it with domain event
    void respondToLoadedLevel(AbstractLevel level);
}
