package core.views;

import core.audio.AudioInteractor;
import core.audio.AudioManagerFactory;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;
import core.preferences.InternalPreferencesInteractorFactory;
import core.user.UserInteractor;
import core.window.WindowInteractor;

public class SupremeInteractorFactory {
    InternalPreferencesInteractorFactory internalPreferencesInteractorFactory;
    public SupremeInteractorFactory(InternalPreferencesInteractorFactory internalPreferencesInteractorFactory) {
        this.internalPreferencesInteractorFactory = internalPreferencesInteractorFactory;
    }

    public HighScoreInteractor getHighScoreInteractor() {
        return new HighScoreInteractorWithGateway(new DbHighScoreGateway(SqlDbFactory.highScoreTable()));
    }

    public AudioInteractor getAudioInteractor() {
        return new AudioInteractor(AudioManagerFactory.getAudioManager(), internalPreferencesInteractorFactory.getPreferencesInteractor());
    }

    public WindowInteractor getWindowInteractor() {
        return new WindowInteractor(internalPreferencesInteractorFactory.getPreferencesInteractor());
    }

    public UserInteractor getUserInteractor() {
        return new UserInteractor(internalPreferencesInteractorFactory.getPreferencesInteractor());
    }
}
