package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import core.preferences.IInternalAudioPreferencesInteractor;

public class AudioInteractor {
    private final AudioControl soundControl;
    private final IInternalAudioPreferencesInteractor internalPreferencesInteractor;

    public AudioInteractor(AudioControl soundControl, IInternalAudioPreferencesInteractor internalPreferencesInteractor) {
        this.soundControl = soundControl;
        this.internalPreferencesInteractor = internalPreferencesInteractor;
    }

    public void loadPreferences() {
        float initialMusicVolume = internalPreferencesInteractor.getMusicVolume();
        soundControl.setMusicVolume(initialMusicVolume);

        boolean musicEnabled = internalPreferencesInteractor.isMusicEnabled();
        soundControl.setMusicEnabled(musicEnabled);

        float initialSoundVolume = internalPreferencesInteractor.getSoundVolume();
        soundControl.setSoundsVolume(initialSoundVolume);

        boolean soundsEnabled = internalPreferencesInteractor.isSoundEffectsEnabled();
        soundControl.setSoundsEnabled(soundsEnabled);
    }

    public void playSoundEffect(Sound sound) {
        soundControl.playSound(sound);
    }

    public void stopAllSoundEffects() {
        soundControl.stopAllSounds();
    }

    public void setSoundsVolume(float volume) {
        soundControl.setSoundsVolume(volume);
        internalPreferencesInteractor.setSoundVolume(volume);
    }

    public float getSoundsVolume() {
        return soundControl.getSoundsVolume();
    }

    public void playBackgroundMusic(Music music, boolean loop) {
        soundControl.playMusic(music, loop);
    }

    public void stopBackgroundMusic() {
        soundControl.stopMusic();
    }

    public void setMusicVolume(float volume) {
        soundControl.setMusicVolume(volume);
        internalPreferencesInteractor.setMusicVolume(volume);
    }

    public float getMusicVolume() {
        return soundControl.getMusicVolume();
    }

    public boolean areSoundsEnabled() {
        return soundControl.isSoundsEnabled();
    }

    public void setSoundsEnabled(boolean enabled) {
        soundControl.setSoundsEnabled(enabled);
        internalPreferencesInteractor.setSoundEffectsEnabled(enabled);
    }

    public boolean isMusicEnabled() {
        return soundControl.isMusicEnabled();
    }

    public void setMusicEnabled(boolean enabled) {
        soundControl.setMusicEnabled(enabled);
        internalPreferencesInteractor.setMusicEnabled(enabled);
    }
}
