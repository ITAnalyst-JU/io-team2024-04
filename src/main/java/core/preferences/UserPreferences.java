package core.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static core.general.Constants.Preferences.*;

// TODO: think about having this in home directory, is it good idea?
// TODO: integration test
// NOTE: UNTESTABLE with UTs

public class UserPreferences {

    private static final String PREFS_NAME = "gradle.demon.adventures.preferences";
    private static final String PREFS_MUSIC_VOLUME = "volume";
    private static final String PREFS_MUSIC_ENABLED = "music.enabled";
    private static final String PREFS_SOUND_ENABLED = "sound.enabled";
    private static final String PREFS_SOUND_VOL = "sound";

    private static final String PREFS_FULLSCREEN_ENABLED = "fullscreen.enabled";
    private static final String PREFS_WINDOW_WIDTH = "window.width";
    private static final String PREFS_WINDOW_HEIGHT = "window.height";
    private static final String PREFS_FPS = "fps";
    private static final String PREFS_VSYNC = "vsync";

    private static final String PREFS_USER_NAME = "user.name";
    
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

    public boolean isFullscreen() {
        return this.getPreferences().getBoolean(PREFS_FULLSCREEN_ENABLED, DEFAULT_FULLSCREEN_ENABLED);
    }

    public void setFullscreen(boolean enabled) {
        this.getPreferences().putBoolean(PREFS_FULLSCREEN_ENABLED, enabled);
        this.getPreferences().flush();
    }

    public int getWindowWidth() {
        return this.getPreferences().getInteger(PREFS_WINDOW_WIDTH, DEFAULT_WINDOW_WIDTH);
    }

    public int getWindowHeight() {
        return this.getPreferences().getInteger(PREFS_WINDOW_HEIGHT, DEFAULT_WINDOW_HEIGHT);
    }

    public void setWindowSize(int width, int height) {
        this.getPreferences().putInteger(PREFS_WINDOW_WIDTH, width);
        this.getPreferences().putInteger(PREFS_WINDOW_HEIGHT, height);
        this.getPreferences().flush();
    }

    public int getFps() {
        return this.getPreferences().getInteger(PREFS_FPS, DEFAULT_FPS);
    }

    public void setFps(int fps) {
        this.getPreferences().putInteger(PREFS_FPS, fps);
        this.getPreferences().flush();
    }

    public void setVSync(boolean enabled) {
        this.getPreferences().putBoolean(PREFS_VSYNC, enabled);
        this.getPreferences().flush();
    }

    public boolean isVSync() {
        return this.getPreferences().getBoolean(PREFS_VSYNC, DEFAULT_VSYNC);
    }

    public String getUserName() {
        // TODO: maybe we can rand it
        return this.getPreferences().getString(PREFS_USER_NAME, DEFAULT_USER_NAME);
    }

    public void setUserName(String name) {
        this.getPreferences().putString(PREFS_USER_NAME, name);
        this.getPreferences().flush();
    }
}