package core.preferences;


import com.badlogic.gdx.Gdx;

public class InternalPreferencesInteractor implements IInternalPreferencesInteractor {
    private final UserPreferences userPreferences;

    public InternalPreferencesInteractor(UserPreferences userPreferences) {
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

    public void setFullscreen(boolean enabled) {
        userPreferences.setFullscreen(enabled);
        if (enabled) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(userPreferences.getWindowWidth(), userPreferences.getWindowHeight());
        }
    }

    public boolean isFullscreen() {
        return userPreferences.isFullscreen();
    }

    public void setWindowSize(int width, int height) {
        userPreferences.setWindowSize(width, height);
        if (!userPreferences.isFullscreen()) {
            Gdx.graphics.setWindowedMode(width, height);
        }
    }

    public int getWindowWidth() {
        return userPreferences.getWindowWidth();
    }

    public int getWindowHeight() {
        return userPreferences.getWindowHeight();
    }

    public void setFps(int fps) {
        userPreferences.setFps(fps);
        Gdx.graphics.setForegroundFPS(fps);
    }
    public int getFps() {
        return userPreferences.getFps();
    }

    public void setVSync(boolean enabled) {
        userPreferences.setVSync(enabled);
        Gdx.graphics.setVSync(enabled);
    }

    public boolean isVSync() {
        return userPreferences.isVSync();
    }

    public void setUserName(String name) {
        userPreferences.setUserName(name);
    }

    public String getUserName() {
        return userPreferences.getUserName();
    }
}
