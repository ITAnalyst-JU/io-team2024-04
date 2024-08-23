package core.music;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import core.general.Observable;
import core.general.Observer;

import java.util.HashMap;
import java.util.Map;

import static desktop.constants.PreferencesConstants.DEFAULT_SOUND_VOLUME;

public class SoundManager extends Observable<Observer<String>> {
    private Map<String, Sound> loadedSounds = new HashMap<>();  // Use a map for loaded sounds
    private float volume = DEFAULT_SOUND_VOLUME;

    public void playSound(String soundPath) {
        Sound sound = loadedSounds.get(soundPath);
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
            loadedSounds.put(soundPath, sound);
        }
        sound.play(getVolume());
        notifyObservers(observer -> observer.respondToEvent("playSound: " + soundPath));
    }

    public void stopAllSounds() {
        for (Sound sound : loadedSounds.values()) {
            sound.stop();
            sound.dispose();
        }
        loadedSounds.clear();
        notifyObservers(observer -> observer.respondToEvent("stopAllSounds"));
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
}
