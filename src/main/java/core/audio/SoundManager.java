package core.audio;

import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

import static core.general.Constants.Preferences.DEFAULT_SOUND_ENABLED;
import static core.general.Constants.Preferences.DEFAULT_SOUND_VOLUME;

public class SoundManager {
    private final Map<Long, Sound> playingSounds = new HashMap<>();
    private float volume = DEFAULT_SOUND_VOLUME;
    private boolean isEnabled = DEFAULT_SOUND_ENABLED;
    private float previousVolume = DEFAULT_SOUND_VOLUME;

    public void playSound(Sound sound) {
        long soundId;
        if (!isEnabled)
            soundId = sound.play(0);
        else
            soundId = sound.play(volume);
        playingSounds.put(soundId, sound);
    }

    public void stopAllSounds() {
        for (Sound sound : playingSounds.values()) {
            sound.stop();
            sound.dispose();
        }
        playingSounds.clear();
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
        if (!enabled) {
            previousVolume = volume;
            setVolume(0);
        } else {
            setVolume(previousVolume);
        }
        isEnabled = enabled;
    }

    public Map<Long, Sound> getPlayingSounds() {
        return playingSounds;
    }
}
