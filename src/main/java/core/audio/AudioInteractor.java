package core.audio;

import core.preferences.IAudioPreferencesInteractor;

public class AudioInteractor {
    private final AudioControl soundControl;
    private final IAudioPreferencesInteractor preferencesInteractor;

    public AudioInteractor(AudioControl soundControl, IAudioPreferencesInteractor preferencesInteractor) {
        this.soundControl = soundControl;
        this.preferencesInteractor = preferencesInteractor;
    }

    public void loadPreferences() {
        float initialMusicVolume = preferencesInteractor.getMusicVolume();
        soundControl.setMusicVolume(initialMusicVolume);

        boolean musicEnabled = preferencesInteractor.isMusicEnabled();
        soundControl.setMusicEnabled(musicEnabled);

        float initialSoundVolume = preferencesInteractor.getSoundVolume();
        soundControl.setSoundsVolume(initialSoundVolume);

        boolean soundsEnabled = preferencesInteractor.isSoundEffectsEnabled();
        soundControl.setSoundsEnabled(soundsEnabled);
    }

    public void playSoundEffect(String soundPath) {
        soundControl.playSound(soundPath);
    }

    public void stopAllSoundEffects() {
        soundControl.stopAllSounds();
    }

    public void setSoundsVolume(float volume) {
        soundControl.setSoundsVolume(volume);
        preferencesInteractor.setSoundVolume(volume);
    }

    public float getSoundsVolume() {
        return soundControl.getSoundsVolume();
    }

    public void playBackgroundMusic(String musicPath, boolean loop) {
        soundControl.playMusic(musicPath, loop);
    }

    public void stopBackgroundMusic() {
        soundControl.stopMusic();
    }

    public void setMusicVolume(float volume) {
        soundControl.setMusicVolume(volume);
        preferencesInteractor.setMusicVolume(volume);
    }

    public float getMusicVolume() {
        return soundControl.getMusicVolume();
    }

    public boolean areSoundsEnabled() {
        return soundControl.isSoundsEnabled();
    }

    public void setSoundsEnabled(boolean enabled) {
        soundControl.setSoundsEnabled(enabled);
        preferencesInteractor.setSoundEffectsEnabled(enabled);
    }

    public boolean isMusicEnabled() {
        return soundControl.isMusicEnabled();
    }

    public void setMusicEnabled(boolean enabled) {
        soundControl.setMusicEnabled(enabled);
        preferencesInteractor.setMusicEnabled(enabled);
    }
}
