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

    // TODO: implemeent these methods
    @Override
    public void setFullscreen(boolean enabled) {
        userPreferences.setFullscreen(enabled);
        if (enabled) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(userPreferences.getWindowWidth(), userPreferences.getWindowHeight());
        }
    }

    @Override
    public boolean isFullscreen() {
        return userPreferences.isFullscreen();
    }

    @Override
    public void setWindowSize(int width, int height) {
        userPreferences.setWindowSize(width, height);
        if (!userPreferences.isFullscreen()) {
            Gdx.graphics.setWindowedMode(width, height);
        }
    }

    @Override
    public int getWindowWidth() {
        return userPreferences.getWindowWidth();
    }

    @Override
    public int getWindowHeight() {
        return userPreferences.getWindowHeight();
    }

    @Override
    public void setFps(int fps) {
        userPreferences.setFps(fps);
        Gdx.graphics.setForegroundFPS(fps);
    }

    @Override
    public int getFps() {
        return userPreferences.getFps();
    }
}
