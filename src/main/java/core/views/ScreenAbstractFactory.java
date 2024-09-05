package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.assets.IAssetManagerFactory;
import core.levels.ILevelManager;

public class ScreenAbstractFactory {
    SupremeInteractorFactory supremeInteractorFactory;
    public ScreenAbstractFactory(SupremeInteractorFactory supremeInteractorFactory) {
        this.supremeInteractorFactory = supremeInteractorFactory;
    }

    public AbstractScreen createScreen(ScreenEnum screenEnum, IAssetManagerFactory assetManagerFactory) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor(), this.supremeInteractorFactory.getUserInteractor());
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor());
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()), assetManagerFactory);
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor(), this.supremeInteractorFactory.getUserInteractor());
            case LEADERBOARD -> new LeaderboardScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor());
            case PAUSE -> new PauseScreen(new Stage(new ScreenViewport()), assetManagerFactory);
            case TUTORIAL -> new TutorialScreen(new Stage(new ScreenViewport()), assetManagerFactory);
            case TUTORIAL2 -> new Tutorial2Screen(new Stage(new ScreenViewport()), assetManagerFactory);
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(ILevelManager level, IAssetManagerFactory assetManagerFactory) {
        return new MainScreen(new Stage(new ScreenViewport()), assetManagerFactory, level, this.supremeInteractorFactory.getHighScoreInteractor(), this.supremeInteractorFactory.getUserInteractor());
    }

    public EndScreen createEndScreen(int levelNumber, IAssetManagerFactory assetManagerFactory) {
        return new EndScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor(), levelNumber);
    }
}
