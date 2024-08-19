package desktop;

public class Config {
    private static final String CONF_MUSIC_VOLUME = "volume";
    private static final String CONF_MUSIC_ENABLED = "music.enabled";
    private static final String CONF_SOUND_ENABLED = "sound.enabled";
    private static final String CONF_SOUND_VOL = "sound";
    private static final String CONF_NAME = "b2dtut"; // TODO: change this
    private static final String CONF_FOREGROUND_FPS = "fps.fg";
    private static final String CONF_WIDTH = "width";
    private static final String CONF_HEIGHT = "height";

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
 
	public void setForegroundFPS(int fps) {
		getConfig().putInt(CONF_FOREGROUND_FPS, fps);
		getConfig().flush();
	}

    public int getForegroundFPS() {
		return getConfig().getInt(CONF_FOREGROUND_FPS, DEFAULT_FOREGROUND_FPS);
	}
 
    public void setWidth(int width) {
		getConfig().putInt(CONF_WIDTH, width);
		getConfig().flush();
	}

    public int getWidth() {
		return getConfig().getInt(CONF_WIDTH, DEFAULT_WINDOW_WIDTH);
	}

    public void setHeight(int height) {
		getConfig().putInt(CONF_HEIGHT, height);
		getConfig().flush();
	}

    public int getHeight() {
		return getConfig().getInt(CONF_HEIGHT, DEFAULT_WINDOW_HEIGHT);
	}
}