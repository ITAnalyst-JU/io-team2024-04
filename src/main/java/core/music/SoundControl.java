package core.music;

public interface SoundControl {
    void playSound(String soundPath);
    void stopAllSounds();
    void playMusic(String musicPath, boolean loop);
    void stopMusic();
}
