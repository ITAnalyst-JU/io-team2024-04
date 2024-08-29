package core.preferences;

public interface IWindowPreferencesInteractor {
    void setFullscreen(boolean enabled);
    boolean isFullscreen();
    void setWindowSize(int width, int height);
    int getWindowWidth();
    int getWindowHeight();
}
