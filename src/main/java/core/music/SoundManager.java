package core.music;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import core.general.Observable;
import core.general.Observer;

import java.util.ArrayList;
import java.util.List;

public class SoundManager extends Observable<Observer<String>> {
    private List<Sound> activeSounds = new ArrayList<>();
    private float volume = 1.0f;

    public void playSound(String soundPath) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        sound.play(volume);
        activeSounds.add(sound);
        notifyObservers(observer -> observer.respondToEvent("playSound: " + soundPath));
    }

    public void stopAllSounds() {
        for (Sound sound : activeSounds) {
            sound.stop();
            sound.dispose();
        }
        activeSounds.clear();
        notifyObservers(observer -> observer.respondToEvent("stopAllSounds"));
    }

    public void setVolume(float volume) {
        if (volume < 0.0f) {
            this.volume = 0.0f;
        } else if (volume > 1.0f) {
            this.volume = 1.0f;
        } else {
            this.volume = volume;
        }
    }

    public float getVolume() {
        return this.volume;
    }

}
