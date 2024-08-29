package core.preferences;

public class InternalPreferencesInteractorFactory {
    private final UserPreferences userPreferences;
    public InternalPreferencesInteractorFactory(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }
    public IInternalPreferencesInteractor getPreferencesInteractor() {
        return new InternalPreferencesInteractor(userPreferences);
    }
}
