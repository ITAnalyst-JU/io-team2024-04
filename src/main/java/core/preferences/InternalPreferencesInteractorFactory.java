package core.preferences;

public class InternalPreferencesInteractorFactory {
    public IInternalPreferencesInteractor getPreferencesInteractor() {
        return new InternalPreferencesInteractor(new UserPreferences());
    }
}
