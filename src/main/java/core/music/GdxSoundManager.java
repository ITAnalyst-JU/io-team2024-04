package core.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GdxSoundManager implements SoundControl {
    private final SoundManager soundManager = new SoundManager();
    private Music currentMusic;

    @Override
    public void playSound(String soundPath) {
        soundManager.playSound(soundPath);
    }

    @Override
    public void stopAllSounds() {
        soundManager.stopAllSounds();
    }

    @Override
    public void playMusic(String musicPath, boolean loop) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        currentMusic.setLooping(loop);
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
}

