package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public interface AudioControl {
    void playSound(Sound sound);
    void stopAllSounds();
    void setSoundsVolume(float volume);
    float getSoundsVolume();
    boolean isSoundsEnabled();
    void setSoundsEnabled(boolean enabled);

    void playMusic(Music music, boolean loop);
    void stopMusic();
    void setMusicVolume(float volume);
    float getMusicVolume();
    boolean isMusicEnabled();
    void setMusicEnabled(boolean enabled);
}

