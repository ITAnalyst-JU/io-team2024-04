package core.assets;

public interface IAssetManagerLoader {
    void loadLoadingScreen();
    void queueImages();

    void queueAtlases();
    void queueSounds();
    void queueMusic();
    void queueFonts();
    void queueSkins();
    void queueLevels();

    boolean loaded();
}
