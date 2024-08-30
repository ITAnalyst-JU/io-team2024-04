package core.general;

public class PreferencesConstants {
    public static final float DEFAULT_MUSIC_VOLUME = 0.5f;
    public static final float DEFAULT_SOUND_VOLUME = 0.5f;
    public static final boolean DEFAULT_MUSIC_ENABLED = true;
    public static final boolean DEFAULT_SOUND_ENABLED = true;

    public static final boolean DEFAULT_FULLSCREEN_ENABLED = false;
    public static final int DEFAULT_WINDOW_WIDTH = 1280;
    public static final int DEFAULT_WINDOW_HEIGHT = 720;
    public static final int DEFAULT_FPS = 60;
    public static final WindowMode[] WINDOW_MODES = {
            new WindowMode(800, 600),
            new WindowMode(1280, 720),
            new WindowMode(1920, 1080),
    };

    public static final int[] FPS_OPTIONS = {30, 60, 120};

    public record WindowMode(
            int width,
            int height
    ) {
    }


}