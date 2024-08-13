package core.views;

import com.badlogic.gdx.math.Vector2;
import core.levels.LevelScreen;
import core.orchestrator.SupremeOrchestrator;
import core.utilities.Constants;

public class ScreenAbstractFactory {
    private SupremeOrchestrator supremeOrchestrator;

    public void setSupremeOrchestrator(SupremeOrchestrator supremeOrchestrator) {
        this.supremeOrchestrator = supremeOrchestrator;
    }

    public AbstractScreen createScreen(ScreenState screenState) {
        return switch (screenState) {
            case MENU -> new MenuScreen(supremeOrchestrator);
            case PREFERENCES -> new PreferencesScreen(supremeOrchestrator);
            case APPLICATION -> new LevelScreen(supremeOrchestrator, new Vector2(110/Constants.Physics.Scale, 500/Constants.Physics.Scale), "map/map2.tmx");
            case ENDGAME -> new EndScreen(supremeOrchestrator);
            case LOADING -> new LoadingScreen(supremeOrchestrator);
            case LEVELSELECTION -> new LevelSelectionScreen(supremeOrchestrator);
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenState);
        };
    }
}
