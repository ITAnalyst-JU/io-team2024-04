package core.window;

import core.preferences.IInternalWindowPreferencesInteractor;
import core.preferences.WindowMode;

public class WindowInteractor {
    private final IInternalWindowPreferencesInteractor internalWindowPreferencesInteractor;

    public WindowInteractor(IInternalWindowPreferencesInteractor internalWindowPreferencesInteractor) {
        this.internalWindowPreferencesInteractor = internalWindowPreferencesInteractor;
    }

    public boolean isFullscreen() {
        return internalWindowPreferencesInteractor.isFullscreen();
    }

    // TODO: implement interactor methods
    public void setFullscreen(boolean enabled) {
        internalWindowPreferencesInteractor.setFullscreen(enabled);
    }

    public WindowMode getWindowMode() {
        return new WindowMode(internalWindowPreferencesInteractor.getWindowWidth(), internalWindowPreferencesInteractor.getWindowHeight());
    }

    public void setWindowMode(WindowMode windowMode) {
        internalWindowPreferencesInteractor.setWindowSize(windowMode.width(), windowMode.height());
    }

    public int getFps() {
        return internalWindowPreferencesInteractor.getFps();
    }

    public void setFps(int fps) {
        internalWindowPreferencesInteractor.setFps(fps);
    }

    public void setVSync(boolean enabled) {
        internalWindowPreferencesInteractor.setVSync(enabled);
    }

    public boolean isVSync() {
        return internalWindowPreferencesInteractor.isVSync();
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
