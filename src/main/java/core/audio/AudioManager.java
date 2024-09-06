package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

import static core.general.Constants.Preferences.DEFAULT_MUSIC_ENABLED;
import static core.general.Constants.Preferences.DEFAULT_MUSIC_VOLUME;
import static core.general.Constants.Preferences.DEFAULT_SOUND_ENABLED;
import static core.general.Constants.Preferences.DEFAULT_SOUND_VOLUME;

public class AudioManager implements AudioControl {
    private Music currentMusic;
    private float musicVolume = DEFAULT_MUSIC_VOLUME;
    private boolean isMusicEnabled = DEFAULT_MUSIC_ENABLED;

    private final Map<Long, Sound> playingSounds = new HashMap<>();
    private float soundVolume = DEFAULT_SOUND_VOLUME;
    private boolean isSoundEnabled = DEFAULT_SOUND_ENABLED;

    @Override
    public void playSound(Sound sound) {
        long soundId;
        if (!isSoundEnabled) {
            soundId = sound.play(0);
        } else {
            soundId = sound.play(soundVolume);
        }
        playingSounds.put(soundId, sound);
    }

    @Override
    public void stopAllSounds() {
        for (Sound sound : playingSounds.values()) {
            sound.stop();
            sound.dispose();
        }
        playingSounds.clear();
    }

    @Override
    public void setSoundsVolume(float volume) {
        this.soundVolume = volume;
        if (isSoundEnabled) {
            for (Map.Entry<Long, Sound> entry : playingSounds.entrySet()) {
                Sound sound = entry.getValue();
                long soundId = entry.getKey();
                sound.setVolume(soundId, soundVolume);
            }
        }
    }

    @Override
    public float getSoundsVolume() {
        return soundVolume;
    }

    @Override
    public boolean isSoundsEnabled() {
        return isSoundEnabled;
    }

    @Override
    public void setSoundsEnabled(boolean enabled) {
        isSoundEnabled = enabled;
        if (!enabled) {
            for (Map.Entry<Long, Sound> entry : playingSounds.entrySet()) {
                Sound sound = entry.getValue();
                long soundId = entry.getKey();
                sound.setVolume(soundId, 0);
            }
        } else {
            for (Map.Entry<Long, Sound> entry : playingSounds.entrySet()) {
                Sound sound = entry.getValue();
                long soundId = entry.getKey();
                sound.setVolume(soundId, soundVolume);
            }
        }
    }

    // Zarządzanie muzyką
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
