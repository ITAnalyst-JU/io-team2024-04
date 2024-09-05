package core.views;

import core.audio.AudioInteractor;
import core.audio.AudioManagerFactory;
import core.network.HighScoreClient;
import core.network.HighScoreNetworkInteractor;
import core.network.HttpClient;
import core.preferences.InternalPreferencesInteractorFactory;
import core.user.UserInteractor;
import core.window.WindowInteractor;

import static core.general.Constants.Preferences.SERVER_URL;

public class SupremeInteractorFactory {
    InternalPreferencesInteractorFactory internalPreferencesInteractorFactory;
    public SupremeInteractorFactory(InternalPreferencesInteractorFactory internalPreferencesInteractorFactory) {
        this.internalPreferencesInteractorFactory = internalPreferencesInteractorFactory;
    }

    public HighScoreNetworkInteractor getHighScoreInteractor() {
        return new HighScoreNetworkInteractor(new HighScoreClient(new HttpClient(SERVER_URL)));
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
