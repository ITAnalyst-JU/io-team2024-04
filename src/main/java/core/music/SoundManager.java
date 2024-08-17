package core.music;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import core.general.Observable;
import core.general.Observer;

import java.util.ArrayList;
import java.util.List;

public class SoundManager extends Observable<Observer<String>> {
    private List<Sound> activeSounds = new ArrayList<>();

    public void playSound(String soundPath) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
        long id = sound.play();
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
}
