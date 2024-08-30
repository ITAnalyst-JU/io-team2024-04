package core.preferences;

public interface IInternalWindowPreferencesInteractor {
    void setFullscreen(boolean enabled);
    boolean isFullscreen();
    void setWindowSize(int width, int height);
    int getWindowWidth();
    int getWindowHeight();

    void setFps(int fps);
    int getFps();

    void setVSync(boolean enabled);
    boolean isVSync();
}
