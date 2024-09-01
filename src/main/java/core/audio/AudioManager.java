package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import static core.general.Constants.Preferences.DEFAULT_MUSIC_ENABLED;
import static core.general.Constants.Preferences.DEFAULT_MUSIC_VOLUME;

public class AudioManager implements AudioControl {
    private final SoundManager soundManager = new SoundManager();
    private Music currentMusic;
    private float musicVolume = DEFAULT_MUSIC_VOLUME;
    private boolean isMusicEnabled = DEFAULT_MUSIC_ENABLED;

    @Override
    public void playSound(Sound sound) {
        soundManager.playSound(sound);
    }

    @Override
    public void stopAllSounds() {
        soundManager.stopAllSounds();
    }

    @Override
    public void setSoundsVolume(float volume) {
        soundManager.setVolume(volume);
    }

    @Override
    public float getSoundsVolume() {
        return soundManager.getVolume();
    }

    @Override
    public boolean isSoundsEnabled() {
        return soundManager.areSoundsEnabled();
    }

    @Override
    public void setSoundsEnabled(boolean enabled) {
        soundManager.setSoundsEnabled(enabled);
    }

    @Override
    public void playMusic(Music music, boolean loop) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = music;
        currentMusic.setLooping(loop);
        if (!isMusicEnabled)
            currentMusic.setVolume(0);
        else
            currentMusic.setVolume(musicVolume);
        currentMusic.play();
    }

    @Override
    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }

    @Override
    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        if (!isMusicEnabled)
            return;
        if (currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }
    }

    @Override
    public float getMusicVolume() {
        return musicVolume;
    }

    @Override
    public boolean isMusicEnabled() {
        return isMusicEnabled;
    }

    @Override
    public void setMusicEnabled(boolean enabled) {
        isMusicEnabled = enabled;
        if (currentMusic != null && !enabled) {
            currentMusic.setVolume(0);
        } else if (currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }
    }
}
