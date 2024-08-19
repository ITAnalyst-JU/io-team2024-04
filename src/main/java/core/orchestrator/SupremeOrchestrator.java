package core.orchestrator;

import com.badlogic.gdx.Game;
import core.general.Observer;
import core.levels.AbstractLevel;
import core.levels.ILevelOrchestrator;
import core.views.MainScreen;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<ScreenEnum> {

    private final ScreenAbstractFactory screenAbstractFactory;
    private final ILevelOrchestrator levelOrchestrator;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;
    private ScreenEnum screenEnum;
    private LevelSelectionScreen levelSelectionScreen;

    public SupremeOrchestrator(ScreenAbstractFactory screenAbstractFactory, ILevelOrchestrator levelOrchestrator) {
        this.screenEnum = ScreenEnum.LOADING;
        this.screenAbstractFactory = screenAbstractFactory;
        this.levelOrchestrator = levelOrchestrator;
    }

    @Override
    public void create() {
        this.changeScreen(screenEnum);
    }

    // TODO: think about screens as temporary objects, delete them and set null
    public void changeScreen(ScreenEnum screenEnum) {
        switch (screenEnum) {
            case ScreenEnum.MENU:
                if (this.menuScreen == null) {
                    this.menuScreen = (MenuScreen) screenAbstractFactory.createScreen(ScreenEnum.MENU);
                    this.menuScreen.addObserver(this);
                }
                this.setScreen(menuScreen);
                this.screenEnum = ScreenEnum.MENU;
                break;
            case ScreenEnum.PREFERENCES:
                if (this.preferencesScreen == null) {
                    this.preferencesScreen = (PreferencesScreen) screenAbstractFactory.createScreen(ScreenEnum.PREFERENCES);
                    this.preferencesScreen.addObserver(this);
                }
                this.setScreen(preferencesScreen);
                this.screenEnum = ScreenEnum.PREFERENCES;
                break;
            case ScreenEnum.GAME:
                if (this.mainScreen != null) {
                    this.mainScreen.removeObserver(this);
                    this.mainScreen.dispose();
                    this.mainScreen = null;
                }
                AbstractLevel currentLevel = levelOrchestrator.getLevel(this.levelSelectionScreen.getNextLevel());
                this.mainScreen = screenAbstractFactory.createMainScreen(currentLevel);
                this.mainScreen.addObserver(this);
                this.setScreen(mainScreen);
                this.screenEnum = ScreenEnum.GAME;
                break;
            case ScreenEnum.ENDGAME:
                if (this.endScreen == null) {
                    this.endScreen = (EndScreen) screenAbstractFactory.createScreen(ScreenEnum.ENDGAME);
                    this.endScreen.addObserver(this);
                }
                this.setScreen(endScreen);
                this.screenEnum = ScreenEnum.ENDGAME;
                break;
            case ScreenEnum.LEVELSELECTION:
                if (this.levelSelectionScreen == null) {
                    this.levelSelectionScreen = (LevelSelectionScreen) screenAbstractFactory.createScreen(ScreenEnum.LEVELSELECTION);
                    this.levelSelectionScreen.addObserver(this);
                }
                this.setScreen(levelSelectionScreen);
                this.screenEnum = ScreenEnum.LEVELSELECTION;
                break;
            default:
                if (this.loadingScreen == null) {
                    this.loadingScreen = (LoadingScreen) screenAbstractFactory.createScreen(ScreenEnum.LOADING);
                    this.loadingScreen.addObserver(this);
                }
                this.setScreen(loadingScreen);
                this.screenEnum = ScreenEnum.LOADING;
                break;
        }
    }

    @Override
    public void respondToEvent(ScreenEnum param) {
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
