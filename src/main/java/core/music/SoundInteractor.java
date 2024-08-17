package core.music;

public class SoundInteractor {
    private final SoundControl soundControl;

    public SoundInteractor(SoundControl soundControl) {
        this.soundControl = soundControl;
    }

    public void playSoundEffect(String soundPath) {
        soundControl.playSound(soundPath);
    }

    public void stopAllSoundEffects() {
        soundControl.stopAllSounds();
    }

    public void playBackgroundMusic(String musicPath, boolean loop) {
        soundControl.playMusic(musicPath, loop);
    }

    public void stopBackgroundMusic() {
        soundControl.stopMusic();
    }
}