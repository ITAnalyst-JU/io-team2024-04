package desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static desktop.constants.PreferencesConstants.*;

// TODO: think about having this in home directory, is it good idea?
// NOTE: PROBABLY UNTESTABLE

public class HomedirOptions {
    private static final String CONF_MUSIC_VOLUME = "volume";
    private static final String CONF_MUSIC_ENABLED = "music.enabled";
    private static final String CONF_SOUND_ENABLED = "sound.enabled";
    private static final String CONF_SOUND_VOL = "sound";
    private static final String CONF_NAME = "gradle.demon.adventures.options";

    protected Preferences getConfig() {
        return Gdx.app.getPreferences(CONF_NAME);
    }

	public boolean isSoundEffectsEnabled() {
		return getConfig().getBoolean(CONF_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
	}
 
	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		getConfig().putBoolean(CONF_SOUND_ENABLED, soundEffectsEnabled);
		getConfig().flush();
	}
 
	public boolean isMusicEnabled() {
		return getConfig().getBoolean(CONF_MUSIC_ENABLED, DEFAULT_MUSIC_ENABLED);
	}
 
	public void setMusicEnabled(boolean musicEnabled) {
		getConfig().putBoolean(CONF_MUSIC_ENABLED, musicEnabled);
		getConfig().flush();
	}
 
	public float getMusicVolume() {
		return getConfig().getFloat(CONF_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
	}
 
	public void setMusicVolume(float volume) {
		getConfig().putFloat(CONF_MUSIC_VOLUME, volume);
		getConfig().flush();
	}
	
	public float getSoundVolume() {
		return getConfig().getFloat(CONF_SOUND_VOL, DEFAULT_SOUND_VOLUME);
	}
 
	public void setSoundVolume(float volume) {
		getConfig().putFloat(CONF_SOUND_VOL, volume);
		getConfig().flush();
	}
}