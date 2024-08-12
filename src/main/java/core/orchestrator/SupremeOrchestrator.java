package core.orchestrator;

import com.badlogic.gdx.Game;
import core.views.*;

public class SupremeOrchestrator extends Game {

    private ScreenAbstractFactory screenAbstractFactory;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;    
    private ScreenState screenState;

    public SupremeOrchestrator() {
        this.screenState = ScreenState.LOADING;
    }

    public void setScreenAbstractFactory(ScreenAbstractFactory screenAbstractFactory) {
        this.screenAbstractFactory = screenAbstractFactory;
    }

    @Override
    public void create() {
        this.changeScreen(screenState);
    }
    public void changeScreen(ScreenState screenState){
        switch(screenState){
            case ScreenState.MENU:
                if (this.menuScreen == null) {
                    this.menuScreen = (MenuScreen) screenAbstractFactory.createScreen(ScreenState.MENU);
                }
                this.setScreen(menuScreen);
                this.screenState = ScreenState.MENU;
                break;
            case ScreenState.PREFERENCES:
                if (this.preferencesScreen == null) {
                    this.preferencesScreen = (PreferencesScreen) screenAbstractFactory.createScreen(ScreenState.PREFERENCES);
                }
                this.setScreen(preferencesScreen);
                this.screenState = ScreenState.PREFERENCES;
                break;
            case ScreenState.APPLICATION:
                if (this.mainScreen == null) {
                    this.mainScreen = (MainScreen) screenAbstractFactory.createScreen(ScreenState.APPLICATION);
                }
                this.setScreen(mainScreen);
                this.screenState = ScreenState.APPLICATION;
                break;
            case ScreenState.ENDGAME:
                if (this.endScreen == null) {
                    this.endScreen = (EndScreen) screenAbstractFactory.createScreen(ScreenState.ENDGAME);
                }
                this.setScreen(endScreen);
                this.screenState = ScreenState.ENDGAME;
                break;
            default:
                if (this.loadingScreen == null) {
                    this.loadingScreen = (LoadingScreen) screenAbstractFactory.createScreen(ScreenState.LOADING);
                }
                this.setScreen(loadingScreen);
                this.screenState = ScreenState.LOADING;
                break;
        }
    }
}
