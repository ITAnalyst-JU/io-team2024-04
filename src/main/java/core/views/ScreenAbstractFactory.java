package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;
import core.levels.AbstractLevel;
import core.audio.AudioManager;
import core.audio.AudioInteractor;
import core.orchestrator.SupremeSurroundingsInteractorFactory;
import core.preferences.PreferencesInteractorFactory;

public class ScreenAbstractFactory {
    // TODO: maybe we can make this on request or throw it to supremeInteractorFactory ;)
    SupremeSurroundingsInteractorFactory supremeSurroundingsInteractorFactory;
    public ScreenAbstractFactory(SupremeSurroundingsInteractorFactory supremeSurroundingsInteractorFactory) {
        this.supremeSurroundingsInteractorFactory = supremeSurroundingsInteractorFactory;

    }

    public AbstractScreen createScreen(ScreenEnum screenEnum) {
        return switch (screenEnum) {
            case MENU -> new MenuScreen(new Stage(new ScreenViewport()), this.supremeSurroundingsInteractorFactory.getAudioInteractor());
            case PREFERENCES -> new PreferencesScreen(new Stage(new ScreenViewport()), this.supremeSurroundingsInteractorFactory.getAudioInteractor(), this.supremeSurroundingsInteractorFactory.getWindowInteractor());
            case ENDGAME -> new EndScreen(new Stage(new ScreenViewport()), this.supremeSurroundingsInteractorFactory.getHighScoreInteractor());
            case LOADING -> new LoadingScreen(new Stage(new ScreenViewport()));
            case LEVELSELECTION -> new LevelSelectionScreen(new Stage(new ScreenViewport()));
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenEnum);
        };
    }

    public MainScreen createMainScreen(AbstractLevel level) {
        return new MainScreen(new Stage(new ScreenViewport()), level, this.supremeSurroundingsInteractorFactory.getHighScoreInteractor());
    }
}
