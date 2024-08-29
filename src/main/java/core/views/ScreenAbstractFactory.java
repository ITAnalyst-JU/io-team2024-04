package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.levels.AbstractLevel;

public class ScreenAbstractFactory {
    // TODO: maybe we can make this on request or throw it to supremeInteractorFactory ;)
    SupremeInteractorFactory supremeInteractorFactory;
    public ScreenAbstractFactory(SupremeInteractorFactory supremeInteractorFactory) {
        this.supremeInteractorFactory = supremeInteractorFactory;

    }

    public AbstractScreen createScreen(ScreenEnum screenEnum) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()), this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor());
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor());
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()), this.supremeInteractorFactory.getHighScoreInteractor());
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()));
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()));
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(AbstractLevel level) {
        return new MainScreen(new Stage(new ScreenViewport()), level, this.supremeInteractorFactory.getHighScoreInteractor());
    }
}
