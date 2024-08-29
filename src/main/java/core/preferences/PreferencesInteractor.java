package core.preferences;


public class PreferencesInteractor implements IPreferencesInteractor {
    private final UserPreferences userPreferences;

    public PreferencesInteractor(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public float getMusicVolume() {
        return userPreferences.getMusicVolume();
    }

    public void setMusicVolume(float volume) {
        userPreferences.setMusicVolume(volume);
    }

    public boolean isMusicEnabled() {
        return userPreferences.isMusicEnabled();
    }

    public void setMusicEnabled(boolean enabled) {
        userPreferences.setMusicEnabled(enabled);
    }

    public float getSoundVolume() {
        return userPreferences.getSoundVolume();
    }

    public void setSoundVolume(float volume) {
        userPreferences.setSoundVolume(volume);
    }

    public boolean isSoundEffectsEnabled() {
        return userPreferences.isSoundEffectsEnabled();
    }

    public void setSoundEffectsEnabled(boolean enabled) {
        userPreferences.setSoundEffectsEnabled(enabled);
    }

    // TODO: implemeent these methods
    @Override
    public void setFullscreen(boolean enabled) {

    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public void setWindowSize(int width, int height) {

    }

    @Override
    public int getWindowWidth() {
        return 0;
    }

    @Override
    public int getWindowHeight() {
        return 0;
    }
}
