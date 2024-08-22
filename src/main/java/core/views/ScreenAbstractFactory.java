package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.levels.AbstractLevel;
import desktop.preferences.PreferencesOrchestrator;

public class ScreenAbstractFactory {
    PreferencesOrchestrator preferencesOrchestrator;

    public ScreenAbstractFactory(PreferencesOrchestrator preferencesOrchestrator) {
        this.preferencesOrchestrator = preferencesOrchestrator;
    }

    public AbstractScreen createScreen(ScreenEnum screenEnum) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()));
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), preferencesOrchestrator);
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()));
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()));
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()));
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(AbstractLevel level) {
        return new MainScreen(new Stage(new ScreenViewport()), level);
    }
}
