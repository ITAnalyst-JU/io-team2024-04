package core.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import static desktop.constants.PreferencesConstants.DEFAULT_MUSIC_VOLUME;

public class AudioManager implements AudioControl {
    private final SoundManager soundManager = new SoundManager();
    private Music currentMusic;
    private float musicVolume = DEFAULT_MUSIC_VOLUME;

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
    public void playMusic(String musicPath, boolean loop) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        currentMusic.setLooping(loop);
        currentMusic.setVolume(musicVolume);  // Use local musicVolume variable
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
        this.musicVolume = volume;  // Set the music volume
        if (currentMusic != null) {
            currentMusic.setVolume(musicVolume);
        }
    }

    @Override
    public float getMusicVolume() {
        return musicVolume;
    }
}
