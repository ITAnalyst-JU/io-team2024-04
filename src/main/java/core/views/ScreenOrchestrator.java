package core.views;

import com.badlogic.gdx.Screen;
import core.assets.AssetManagerFactory;
import core.general.Observable;
import core.general.Observer;
import core.levels.LevelManager;
import core.levels.LevelEnum;
import core.orchestrator.DomainEventEnum;

import java.util.Map;
import java.util.Objects;

public class ScreenOrchestrator extends Observable<Observer<DomainEventEnum>> implements IScreenOrchestrator, Observer<ScreenEnum> {
    private final ScreenAbstractFactory screenAbstractFactory;
    private final Map<ScreenEnum, AbstractScreen> screens;
    private ScreenEnum nextScreenEnum;

    public ScreenOrchestrator(ScreenAbstractFactory screenAbstractFactory, Map<ScreenEnum, AbstractScreen> screens) {
        this.screenAbstractFactory = screenAbstractFactory;
        this.screens = screens;
        this.nextScreenEnum = ScreenEnum.LOADING;
    }

    public Screen getScreen(ScreenEnum screenEnum, AssetManagerFactory assetManagerFactory) {
        if (!screens.containsKey(screenEnum)) {
            this.loadScreen(screenEnum, assetManagerFactory);
        }
        return screens.get(screenEnum);
    }

    public ScreenEnum getNextScreenEnum() {
        return nextScreenEnum;
    }

    @Override
    public void respondToLoadedLevel(LevelManager level, AssetManagerFactory assetManagerFactory) {
        this.loadMainScreen(level, assetManagerFactory);
    }

    private void loadMainScreen(LevelManager level, AssetManagerFactory assetManagerFactory) {
        AbstractScreen screen = screenAbstractFactory.createMainScreen(level, assetManagerFactory);
        screens.put(ScreenEnum.GAME, screen);
        screen.addObserver(this);
    }

    private void loadScreen(ScreenEnum screenEnum, AssetManagerFactory assetManagerFactory) {
        AbstractScreen screen;
        screen = screenAbstractFactory.createScreen(screenEnum, assetManagerFactory);
        screens.put(screenEnum, screen);
        screen.addObserver(this);
    }

    @Override
    public LevelEnum getNextLevel() {
        return ((LevelSelectionScreen) screens.get(ScreenEnum.LEVELSELECTION)).getNextLevel();
    }

    private void notifyOrchestrator(DomainEventEnum domainEventEnum) {
        notifyObservers(observer -> observer.respondToEvent(domainEventEnum));
    }

    // TODO: think about screens as temporary objects, delete them and set null
    private void changeScreen(ScreenEnum screenEnum) {
        this.nextScreenEnum = screenEnum;
        if (Objects.requireNonNull(screenEnum) == ScreenEnum.GAME) {
            notifyOrchestrator(DomainEventEnum.NEEDLEVEL);
        } else if (Objects.requireNonNull(screenEnum) == ScreenEnum.RESUME_GAME) {
            notifyOrchestrator(DomainEventEnum.RESUME_LEVEL);
        } else {
            notifyOrchestrator(DomainEventEnum.CHANGESCREEN);
        }
    }

    private void stopObservingScreens() {
        for (AbstractScreen screen : screens.values()) {
            screen.removeObserver(this);
        }
    }
    @Override
    public void dispose() {
        stopObservingScreens();
        screens.clear();
    }

    @Override
    public void respondToEvent(ScreenEnum param) {
        this.changeScreen(param);
    }
}
