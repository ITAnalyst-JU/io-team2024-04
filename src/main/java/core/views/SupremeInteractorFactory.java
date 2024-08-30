package core.views;

import core.audio.AudioInteractor;
import core.audio.AudioManager;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;
import core.preferences.InternalPreferencesInteractorFactory;
import core.window.WindowInteractor;

public class SupremeInteractorFactory {
    InternalPreferencesInteractorFactory internalPreferencesInteractorFactory;
    AudioInteractor audioInteractor;
    public SupremeInteractorFactory(InternalPreferencesInteractorFactory internalPreferencesInteractorFactory) {
        this.internalPreferencesInteractorFactory = internalPreferencesInteractorFactory;
    }

    public HighScoreInteractor getHighScoreInteractor() {
        return new HighScoreInteractorWithGateway(new DbHighScoreGateway(SqlDbFactory.highScoreTable()));
    }

    // NOTE: also our enemy singleton pattern

    public AudioInteractor getAudioInteractor() {
        if (audioInteractor == null) {
            audioInteractor = new AudioInteractor(new AudioManager(), internalPreferencesInteractorFactory.getPreferencesInteractor());
        }
        return audioInteractor;
    }

    public WindowInteractor getWindowInteractor() {
        return new WindowInteractor(internalPreferencesInteractorFactory.getPreferencesInteractor());
    }

}
