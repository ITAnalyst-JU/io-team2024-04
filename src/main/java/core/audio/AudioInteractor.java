package core.audio;

import desktop.preferences.PreferencesOrchestrator;

public class AudioInteractor {
    private final AudioControl soundControl;
    private final PreferencesOrchestrator preferencesOrchestrator;

    public AudioInteractor(AudioControl soundControl, PreferencesOrchestrator preferencesOrchestrator) {
        this.soundControl = soundControl;
        this.preferencesOrchestrator = preferencesOrchestrator;
    }

    public void loadPreferences() {
        float initialMusicVolume = preferencesOrchestrator.getMusicVolume();
        soundControl.setMusicVolume(initialMusicVolume);

        float initialSoundVolume = preferencesOrchestrator.getSoundVolume();
        soundControl.setSoundsVolume(initialSoundVolume);
    }

    public void playSoundEffect(String soundPath) {
        soundControl.playSound(soundPath);
    }

    public void stopAllSoundEffects() {
        soundControl.stopAllSounds();
    }

    public void setSoundsVolume(float volume) {
        soundControl.setSoundsVolume(volume);
        preferencesOrchestrator.setSoundVolume(volume);  // Save to preferences
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
        preferencesOrchestrator.setMusicVolume(volume);  // Save to preferences
    }

    public float getMusicVolume() {
        return soundControl.getMusicVolume();
    }
}
