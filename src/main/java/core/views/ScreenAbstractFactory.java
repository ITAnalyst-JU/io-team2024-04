package core.views;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.orchestrator.SupremeOrchestrator;
import core.utilities.Constants;

public class ScreenAbstractFactory {
    public AbstractScreen createScreen(ScreenState screenState) {
        return switch (screenState) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()));
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()));
            case APPLICATION -> new MainScreen(new Stage(new ScreenViewport()), new Vector2(110/Constants.Physics.Scale, 500/Constants.Physics.Scale), "map/map2.tmx");
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()));
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()));
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()));
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenState);
        };
    }
}
