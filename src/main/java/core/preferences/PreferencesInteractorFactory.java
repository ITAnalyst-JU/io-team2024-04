package core.preferences;

public class PreferencesInteractorFactory {
    private final UserPreferences userPreferences;
    public PreferencesInteractorFactory(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }
    public IPreferencesInteractor getPreferencesInteractor() {
        return new PreferencesInteractor(userPreferences);
    }
}
