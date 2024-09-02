package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.assets.AssetManagerFactory;
import core.levels.ILevelManager;

public class ScreenAbstractFactory {
    // TODO: maybe we can make this on request or throw it to supremeInteractorFactory ;)
    SupremeInteractorFactory supremeInteractorFactory;
    public ScreenAbstractFactory(SupremeInteractorFactory supremeInteractorFactory) {
        this.supremeInteractorFactory = supremeInteractorFactory;
    }

    public AbstractScreen createScreen(ScreenEnum screenEnum, AssetManagerFactory assetManagerFactory) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor(), this.supremeInteractorFactory.getUserInteractor());
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getAudioInteractor(), this.supremeInteractorFactory.getWindowInteractor());
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor());
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()), assetManagerFactory);
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor(), this.supremeInteractorFactory.getUserInteractor());
            case LEADERBOARD -> new LeaderboardScreen(new Stage(new ScreenViewport()), assetManagerFactory, this.supremeInteractorFactory.getHighScoreInteractor());
            case PAUSE -> new PauseScreen(new Stage(new ScreenViewport()), assetManagerFactory);
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(ILevelManager level, AssetManagerFactory assetManagerFactory) {
        return new MainScreen(new Stage(new ScreenViewport()), assetManagerFactory, level, this.supremeInteractorFactory.getHighScoreInteractor(), this.supremeInteractorFactory.getUserInteractor());
    }
}
