package core.audio;

public interface AudioControl {
    void playSound(String soundPath);
    void stopAllSounds();
    void setSoundsVolume(float volume);
    float getSoundsVolume();
    boolean isSoundsEnabled();
    void setSoundsEnabled(boolean enabled);

    void playMusic(String musicPath, boolean loop);
    void stopMusic();
    void setMusicVolume(float volume);
    float getMusicVolume();
    boolean isMusicEnabled();
    void setMusicEnabled(boolean enabled);
}

