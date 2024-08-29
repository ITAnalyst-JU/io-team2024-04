package core.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static core.utilities.PreferencesConstants.*;

// TODO: think about having this in home directory, is it good idea?
// TODO: integration test
// NOTE: UNTESTABLE with UTs

public class UserPreferences {
    private static final String PREFS_MUSIC_VOLUME = "volume";
    private static final String PREFS_MUSIC_ENABLED = "music.enabled";
    private static final String PREFS_SOUND_ENABLED = "sound.enabled";
    private static final String PREFS_SOUND_VOL = "sound";
    
    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return this.getPreferences().getBoolean(PREFS_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        this.getPreferences().putBoolean(PREFS_SOUND_ENABLED, soundEffectsEnabled);
        this.getPreferences().flush();
    }

    public boolean isMusicEnabled() {
        return this.getPreferences().getBoolean(PREFS_MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.getPreferences().putBoolean(PREFS_MUSIC_ENABLED, musicEnabled);
        this.getPreferences().flush();
    }

    public float getMusicVolume() {
        return this.getPreferences().getFloat(PREFS_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
    }

    public void setMusicVolume(float volume) {
        this.getPreferences().putFloat(PREFS_MUSIC_VOLUME, volume);
        this.getPreferences().flush();
    }

    public float getSoundVolume() {
        return this.getPreferences().getFloat(PREFS_SOUND_VOL, DEFAULT_SOUND_VOLUME);
    }

    public void setSoundVolume(float volume) {
        this.getPreferences().putFloat(PREFS_SOUND_VOL, volume);
        this.getPreferences().flush();
    }
}