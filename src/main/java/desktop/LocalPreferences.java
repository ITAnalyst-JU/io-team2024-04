package desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static desktop.constants.PreferencesConstants.*;

// TODO: think about having this in home directory, is it good idea?
// NOTE: PROBABLY UNTESTABLE

public class LocalPreferences {
    private static final String PREFS_MUSIC_VOLUME = "volume";
    private static final String PREFS_MUSIC_ENABLED = "music.enabled";
    private static final String PREFS_SOUND_ENABLED = "sound.enabled";
    private static final String PREFS_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "gradle.demon.adventures.options";

	// TODO: dependency injection??? then I can test
    private Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

	public boolean isSoundEffectsEnabled() {
		return getPrefs().getBoolean(PREFS_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
	}
 
	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		getPrefs().putBoolean(PREFS_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}
 
	public boolean isMusicEnabled() {
		return getPrefs().getBoolean(PREFS_MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED);
	}
 
	public void setMusicEnabled(boolean musicEnabled) {
		getPrefs().putBoolean(PREFS_MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}
 
	public float getMusicVolume() {
		return getPrefs().getFloat(PREFS_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
	}
 
	public void setMusicVolume(float volume) {
		getPrefs().putFloat(PREFS_MUSIC_VOLUME, volume);
		getPrefs().flush();
	}
	
	public float getSoundVolume() {
		return getPrefs().getFloat(PREFS_SOUND_VOL, DEFAULT_SOUND_VOLUME);
	}
 
	public void setSoundVolume(float volume) {
		getPrefs().putFloat(PREFS_SOUND_VOL, volume);
		getPrefs().flush();
	}
}