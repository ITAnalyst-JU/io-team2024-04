package core.orchestrator;

import com.badlogic.gdx.Game;
import core.general.Observer;
import core.levels.LevelOrchestrator;
import core.views.MainScreen;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<ScreenState> {

    private ScreenAbstractFactory screenAbstractFactory;
    private LevelOrchestrator levelOrchestrator;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;    
    private ScreenState screenState;
    private LevelSelectionScreen levelSelectionScreen;

    public SupremeOrchestrator() {
        this.screenState = ScreenState.LOADING;
    }

    public void setScreenAbstractFactory(ScreenAbstractFactory screenAbstractFactory) {
        this.screenAbstractFactory = screenAbstractFactory;
    }

    public void setLevelOrchestrator(LevelOrchestrator levelOrchestrator) {
        this.levelOrchestrator = levelOrchestrator;
    }

    @Override
    public void create() {
        this.changeScreen(screenState);
    }
    public void changeScreen(ScreenState screenState){
        switch (screenState) {
            case ScreenState.MENU:
                if (this.menuScreen == null) {
                    this.menuScreen = (MenuScreen) screenAbstractFactory.createScreen(ScreenState.MENU);
                    this.menuScreen.addObserver(this);
                }
                this.setScreen(menuScreen);
                this.screenState = ScreenState.MENU;
                break;
            case ScreenState.PREFERENCES:
                if (this.preferencesScreen == null) {
                    this.preferencesScreen = (PreferencesScreen) screenAbstractFactory.createScreen(ScreenState.PREFERENCES);
                    this.preferencesScreen.addObserver(this);
                }
                this.setScreen(preferencesScreen);
                this.screenState = ScreenState.PREFERENCES;
                break;
            case ScreenState.APPLICATION:
                if (this.mainScreen == null) {
                    this.mainScreen = (MainScreen) screenAbstractFactory.createScreen(ScreenState.APPLICATION);
                    this.mainScreen.addObserver(this);
                }
                this.setScreen(mainScreen);
                this.screenState = ScreenState.APPLICATION;
                break;
            case ScreenState.ENDGAME:
                if (this.endScreen == null) {
                    this.endScreen = (EndScreen) screenAbstractFactory.createScreen(ScreenState.ENDGAME);
                    this.endScreen.addObserver(this);
                }
                this.setScreen(endScreen);
                this.screenState = ScreenState.ENDGAME;
                break;
            case ScreenState.LEVELSELECTION:
                if (this.levelSelectionScreen == null) {
                    this.levelSelectionScreen = (LevelSelectionScreen) screenAbstractFactory.createScreen(ScreenState.LEVELSELECTION);
                    this.levelSelectionScreen.addObserver(this);
                }
                this.setScreen(levelSelectionScreen);
                this.screenState = ScreenState.LEVELSELECTION;
                break;
            default:
                if (this.loadingScreen == null) {
                    this.loadingScreen = (LoadingScreen) screenAbstractFactory.createScreen(ScreenState.LOADING);
                    this.loadingScreen.addObserver(this);
                }
                this.setScreen(loadingScreen);
                this.screenState = ScreenState.LOADING;
                break;
        }
    }

    @Override
    public void respondToEvent(ScreenState param) {
        this.changeScreen(param);
    }

    @Override
    public void dispose() {
        super.dispose();
        stopObservingScreens();
    }

    // TODO: this doesn't make sense, because we are the longest living class
    private void stopObservingScreens() {
        if (this.menuScreen != null) this.menuScreen.removeObserver(this);
        if (this.preferencesScreen != null) this.preferencesScreen.removeObserver(this);
        if (this.mainScreen != null) this.mainScreen.removeObserver(this);
        if (this.endScreen != null) this.endScreen.removeObserver(this);
        if (this.levelSelectionScreen != null) this.levelSelectionScreen.removeObserver(this);
        if (this.loadingScreen != null) this.loadingScreen.removeObserver(this);
    }
}
