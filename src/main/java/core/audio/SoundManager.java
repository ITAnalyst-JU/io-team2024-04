package core.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

import static desktop.constants.PreferencesConstants.DEFAULT_SOUND_ENABLED;
import static desktop.constants.PreferencesConstants.DEFAULT_SOUND_VOLUME;

public class SoundManager {
    private Map<String, Sound> loadedSounds = new HashMap<>();
    private Map<Long, Sound> playingSounds = new HashMap<>();
    private float volume = DEFAULT_SOUND_VOLUME;
    private boolean isEnabled = DEFAULT_SOUND_ENABLED;

    public void playSound(String soundPath) {
        Sound sound = loadedSounds.get(soundPath);
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
            loadedSounds.put(soundPath, sound);
        }

        long soundId;
        if (!isEnabled)
            soundId = sound.play(0);
        else
            soundId = sound.play(volume);
        playingSounds.put(soundId, sound);
    }

    public void stopAllSounds() {
        for (Sound sound : loadedSounds.values()) {
            sound.stop();
            sound.dispose();
        }
        loadedSounds.clear();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (!isEnabled)
            return;
        for (Map.Entry<Long, Sound> entry : playingSounds.entrySet()) {
            Sound sound = entry.getValue();
            long soundId = entry.getKey();
            sound.setVolume(soundId, volume);
        }
    }

    public float getVolume() {
        return volume;
    }

    public boolean areSoundsEnabled() {
        return isEnabled;
    }

    public void setSoundsEnabled(boolean enabled) {
        isEnabled = enabled;
        if (!enabled) {
            setVolume(0);
        } else {
            setVolume(volume);
        }
    }
}
