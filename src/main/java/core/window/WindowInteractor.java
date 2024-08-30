package core.window;

import core.preferences.IInternalWindowPreferencesInteractor;

public class WindowInteractor {
    private final IInternalWindowPreferencesInteractor internalWindowPreferencesInteractor;

    public WindowInteractor(IInternalWindowPreferencesInteractor internalWindowPreferencesInteractor) {
        this.internalWindowPreferencesInteractor = internalWindowPreferencesInteractor;
    }

    // TODO: implement interactor methods
    public void setFullscreen(boolean enabled) {
        internalWindowPreferencesInteractor.setFullscreen(enabled);
    }
    public boolean isFullscreen() {
        return internalWindowPreferencesInteractor.isFullscreen();
    }
    public void setWindowSize(int width, int height) {
        internalWindowPreferencesInteractor.setWindowSize(width, height);
    }
    public int getWindowWidth() {
        return internalWindowPreferencesInteractor.getWindowWidth();
    }
    public int getWindowHeight() {
        return internalWindowPreferencesInteractor.getWindowHeight();
    }

    public void setFps(int fps) {
        internalWindowPreferencesInteractor.setFps(fps);
    }
    public int getFps() {
        return internalWindowPreferencesInteractor.getFps();
    }
    public void loadPreferences() {
        boolean isFullscreen = internalWindowPreferencesInteractor.isFullscreen();
        internalWindowPreferencesInteractor.setFullscreen(isFullscreen);

        int width = internalWindowPreferencesInteractor.getWindowWidth();
        int height = internalWindowPreferencesInteractor.getWindowHeight();
        internalWindowPreferencesInteractor.setWindowSize(width, height);

        int fps = internalWindowPreferencesInteractor.getFps();
        internalWindowPreferencesInteractor.setFps(fps);
    }

}
