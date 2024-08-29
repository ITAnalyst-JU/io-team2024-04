package core.preferences;

public interface IAudioPreferencesInteractor {
    float getMusicVolume();
    void setMusicVolume(float volume);
    boolean isMusicEnabled();
    void setMusicEnabled(boolean enabled);
    float getSoundVolume();
    void setSoundVolume(float volume);
    boolean isSoundEffectsEnabled();
    void setSoundEffectsEnabled(boolean enabled);
}
