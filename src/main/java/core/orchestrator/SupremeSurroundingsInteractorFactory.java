package core.orchestrator;

import core.audio.AudioInteractor;
import core.audio.AudioManager;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;
import core.preferences.PreferencesInteractorFactory;
import core.window.WindowInteractor;

public class SupremeSurroundingsInteractorFactory {
    HighScoreInteractor highScoreInteractor;
    PreferencesInteractorFactory preferencesInteractorFactory;
    AudioInteractor audioInteractor;
    WindowInteractor windowInteractor;
    public SupremeSurroundingsInteractorFactory(PreferencesInteractorFactory preferencesInteractorFactory) {
        this.preferencesInteractorFactory = preferencesInteractorFactory;
    }

    // NOTE: lazy evaluation
    // also our enemy singleton pattern - maybe just leave it

    public HighScoreInteractor getHighScoreInteractor() {
        if (highScoreInteractor == null) {
            highScoreInteractor = new HighScoreInteractorWithGateway(new DbHighScoreGateway(SqlDbFactory.highScoreTable()));
        }
        return highScoreInteractor;
    }

    public AudioInteractor getAudioInteractor() {
        if (audioInteractor == null) {
            audioInteractor = new AudioInteractor(new AudioManager(), preferencesInteractorFactory.getPreferencesInteractor());
        }
        return audioInteractor;
    }

    public WindowInteractor getWindowInteractor() {
        if (windowInteractor == null) {
            windowInteractor = new WindowInteractor(preferencesInteractorFactory.getPreferencesInteractor());
        }
        return windowInteractor;
    }

}
