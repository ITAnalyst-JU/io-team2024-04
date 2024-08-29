package core.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import static core.utilities.PreferencesConstants.DEFAULT_MUSIC_ENABLED;
import static core.utilities.PreferencesConstants.DEFAULT_MUSIC_VOLUME;

public class AudioManager implements AudioControl {
    private final SoundManager soundManager = new SoundManager();
    private Music currentMusic;
    private float musicVolume = DEFAULT_MUSIC_VOLUME;
    private boolean isMusicEnabled = DEFAULT_MUSIC_ENABLED;

    @Override
    public void playSound(String soundPath) {
        soundManager.playSound(soundPath);
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
    public void playMusic(String musicPath, boolean loop) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
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
