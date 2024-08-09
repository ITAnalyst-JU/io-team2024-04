package core.orchestrator;

import com.badlogic.gdx.Game;
import core.views.*;

public class SupremeOrchestrator extends Game {
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;    
    private ScreenState screenState;
    SupremeOrchestrator(
        LoadingScreen loadingScreen,
        PreferencesScreen preferencesScreen,
        MenuScreen menuScreen,
        MainScreen mainScreen,
        EndScreen endScreen) {
        screenState            = ScreenState.LOADGING;
        this.loadingScreen     = loadingScreen;
        this.preferencesScreen = preferencesScreen;
        this.menuScreen        = menuScreen;
        this.mainScreen        = mainScreen;
        this.endScreen         = endScreen;
    }
}
