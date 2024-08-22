package desktop.preferences;

public class PreferencesOrchestrator {
    private final LocalPreferences localPreferences;

    public PreferencesOrchestrator(LocalPreferences localPreferences) {
        this.localPreferences = localPreferences;
    }

    public float getMusicVolume() {
        return localPreferences.getMusicVolume();
    }

    public void setMusicVolume(float volume) {
        localPreferences.setMusicVolume(volume);
    }

    public boolean isMusicEnabled() {
        return localPreferences.isMusicEnabled();
    }

    public void setMusicEnabled(boolean enabled) {
        localPreferences.setMusicEnabled(enabled);
    }

    public float getSoundVolume() {
        return localPreferences.getSoundVolume();
    }

    public void setSoundVolume(float volume) {
        localPreferences.setSoundVolume(volume);
    }

    public boolean isSoundEffectsEnabled() {
        return localPreferences.isSoundEffectsEnabled();
    }

    public void setSoundEffectsEnabled(boolean enabled) {
        localPreferences.setSoundEffectsEnabled(enabled);
    }
}
