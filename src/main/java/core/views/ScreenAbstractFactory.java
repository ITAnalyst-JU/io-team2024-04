package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;
import core.levels.AbstractLevel;
import core.music.GdxSoundManager;
import core.music.SoundInteractor;
import desktop.preferences.LocalPreferences;
import desktop.preferences.PreferencesOrchestrator;

public class ScreenAbstractFactory {
    SoundInteractor soundInteractor;
    HighScoreInteractor highScoreInteractor;

    public ScreenAbstractFactory() {
        PreferencesOrchestrator preferencesOrchestrator = new PreferencesOrchestrator(new LocalPreferences());
        this.soundInteractor = new SoundInteractor(new GdxSoundManager(), preferencesOrchestrator);
        this.highScoreInteractor = new HighScoreInteractorWithGateway(new DbHighScoreGateway(SqlDbFactory.highScoreTable()));
    }

    public AbstractScreen createScreen(ScreenEnum screenEnum) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()), soundInteractor);
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), soundInteractor);
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()), highScoreInteractor);
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()));
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()));
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(AbstractLevel level) {
        return new MainScreen(new Stage(new ScreenViewport()), level, highScoreInteractor);
    }
}
