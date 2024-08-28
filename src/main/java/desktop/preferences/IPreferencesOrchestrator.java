package desktop.preferences;

// NOTE: strategy design pattern ;)
public interface IPreferencesOrchestrator {
    float getMusicVolume();
    void setMusicVolume(float volume);
    boolean isMusicEnabled();
    void setMusicEnabled(boolean enabled);
    float getSoundVolume();
    void setSoundVolume(float volume);
    boolean isSoundEffectsEnabled();
    void setSoundEffectsEnabled(boolean enabled);

    //TODO more options like fullscreen or window size
}
