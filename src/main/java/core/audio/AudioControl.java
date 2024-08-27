package core.audio;

public interface AudioControl {
    void playSound(String soundPath);
    void stopAllSounds();
    void setSoundsVolume(float volume);
    float getSoundsVolume();

    void playMusic(String musicPath, boolean loop);
    void stopMusic();
    void setMusicVolume(float volume);
    float getMusicVolume();
}

