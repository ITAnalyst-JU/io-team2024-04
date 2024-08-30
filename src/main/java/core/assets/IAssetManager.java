package core.assets;

public interface IAssetManager {
    void queueAddImages();
    void queueAddSounds();
    void queueAddMusic();
    void queueAddFonts();

    void waitForImages();
    void waitForSounds();
    void waitForMusic();
    void waitForFonts();

}
