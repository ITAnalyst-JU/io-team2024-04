//package core.views;
//
//import com.badlogic.gdx.Screen;
//import core.general.Observer;
//import core.levels.AbstractLevel;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ScreenOrchestrator implements IScreenOrchestrator, Observer<ScreenEnum> {
//    private final ScreenAbstractFactory screenAbstractFactory;
//    private final Map<ScreenEnum, Screen> screens;
//    private ScreenEnum currentScreenEnum;
//
//    public ScreenOrchestrator(ScreenAbstractFactory screenAbstractFactory, Map<ScreenEnum, Screen> screens) {
//        this.screenAbstractFactory = screenAbstractFactory;
//        this.screens = screens;
//    }
//
//    public Screen getScreen(ScreenEnum screenEnum) {
//        if (!screens.containsKey(screenEnum)) {
//            this.loadScreen(screenEnum);
//        }
//        return screens.get(screenEnum);
//    }
//
//    private void loadScreen(ScreenEnum screenEnum) {
//        Screen screen;
//        if (screenEnum == ScreenEnum.GAME) {
//            screen = screenAbstractFactory.createMainScreen();
//        }
//        Screen screen = screenAbstractFactory.createScreen(screenEnum);
//        levels.put(levelNumber, level);
//    }
//
//    public void notifyOrchestrator(ScreenEnum screenEnum) {
//
//    }
//
//    // TODO: think about screens as temporary objects, delete them and set null
//    public void changeScreen(ScreenEnum screenEnum) {
//        switch (screenEnum) {
//            case ScreenEnum.MENU:
//                if (this.menuScreen == null) {
//                    this.menuScreen = (MenuScreen) screenAbstractFactory.createScreen(ScreenEnum.MENU);
//                    this.menuScreen.addObserver(this);
//                }
//                this.currentScreen = menuScreen;
//                this.screenEnum = ScreenEnum.MENU;
//                break;
//            case ScreenEnum.PREFERENCES:
//                if (this.preferencesScreen == null) {
//                    this.preferencesScreen = (PreferencesScreen) screenAbstractFactory.createScreen(ScreenEnum.PREFERENCES);
//                    this.preferencesScreen.addObserver(this);
//                }
//                this.currentScreen = preferencesScreen;
//                this.screenEnum = ScreenEnum.PREFERENCES;
//                break;
//            case ScreenEnum.GAME:
//                if (this.mainScreen != null) {
//                    this.mainScreen.removeObserver(this);
//                    this.mainScreen.dispose();
//                    this.mainScreen = null;
//                }
//                // TODO: notify orchestrator to get level, maybe set screen to loading
//                AbstractLevel currentLevel = levelOrchestrator.getLevel(this.levelSelectionScreen.getNextLevel());
//                this.mainScreen = screenAbstractFactory.createMainScreen(currentLevel);
//                this.mainScreen.addObserver(this);
//                this.currentScreen = mainScreen;
//                this.screenEnum = ScreenEnum.GAME;
//                break;
//            case ScreenEnum.ENDGAME:
//                if (this.endScreen == null) {
//                    this.endScreen = (EndScreen) screenAbstractFactory.createScreen(ScreenEnum.ENDGAME);
//                    this.endScreen.addObserver(this);
//                }
//                this.currentScreen = endScreen;
//                this.screenEnum = ScreenEnum.ENDGAME;
//                break;
//            case ScreenEnum.LEVELSELECTION:
//                if (this.levelSelectionScreen == null) {
//                    this.levelSelectionScreen = (LevelSelectionScreen) screenAbstractFactory.createScreen(ScreenEnum.LEVELSELECTION);
//                    this.levelSelectionScreen.addObserver(this);
//                }
//                this.currentScreen = levelSelectionScreen;
//                this.screenEnum = ScreenEnum.LEVELSELECTION;
//                break;
//            default:
//                if (this.loadingScreen == null) {
//                    this.loadingScreen = (LoadingScreen) screenAbstractFactory.createScreen(ScreenEnum.LOADING);
//                    this.loadingScreen.addObserver(this);
//                }
//                this.currentScreen = loadingScreen;
//                this.screenEnum = ScreenEnum.LOADING;
//                break;
//        }
//    }
//
//    @Override
//    public void respondToEvent(ScreenEnum param) {
//        this.changeScreen(param);
//    }
//}
