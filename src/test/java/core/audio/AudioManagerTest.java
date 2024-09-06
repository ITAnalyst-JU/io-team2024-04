package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.jupiter.api.Test;

import static core.general.Constants.Preferences.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class AudioManagerTest {

    // Sound Management Tests

    @Test
    void testPlaySoundWhenEnabled() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.setSoundsEnabled(true);
        audioManager.setSoundsVolume(0.5f);

        audioManager.playSound(sound);

        verify(sound).play(0.5f);
    }

    @Test
    void testPlaySoundWhenDisabled() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.setSoundsEnabled(false);

        audioManager.playSound(sound);

        verify(sound).play(0);
    }

    @Test
    void testStopAllSounds() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.setSoundsEnabled(true);
        audioManager.playSound(sound);

        audioManager.stopAllSounds();

        verify(sound).stop();
        verify(sound).dispose();
    }

    @Test
    void testSetSoundsVolumeWhenEnabled() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.setSoundsEnabled(true);
        audioManager.playSound(sound);

        audioManager.setSoundsVolume(0.8f);

        verify(sound).setVolume(anyLong(), eq(0.8f));
    }

    @Test
    void testSetSoundsVolumeWhenDisabled() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.setSoundsEnabled(false);
        audioManager.playSound(sound);

        audioManager.setSoundsVolume(0.8f);

        verify(sound, never()).setVolume(anyLong(), anyFloat());
    }

    @Test
    void testGetSoundsVolume() {
        var audioManager = new AudioManager();
        assertThat(audioManager.getSoundsVolume()).isEqualTo(DEFAULT_SOUND_VOLUME);
    }

    @Test
    void testIsSoundsEnabled() {
        var audioManager = new AudioManager();
        assertThat(audioManager.isSoundsEnabled()).isEqualTo(DEFAULT_SOUND_ENABLED);
    }

    @Test
    void testSetSoundsEnabled() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);
        audioManager.playSound(sound);

        audioManager.setSoundsEnabled(true);
        assertThat(audioManager.isSoundsEnabled()).isTrue();

        audioManager.setSoundsEnabled(false);
        assertThat(audioManager.isSoundsEnabled()).isFalse();

        verify(sound).setVolume(anyLong(), eq(0f));
    }

    @Test
    void testSetSoundsEnabledWhenSoundIsPlaying() {
        var audioManager = new AudioManager();
        var sound = mock(Sound.class);

        audioManager.setSoundsEnabled(true);
        audioManager.setSoundsVolume(0.6f);
        audioManager.playSound(sound);

        verify(sound).play(0.6f);

        audioManager.setSoundsEnabled(false);
        verify(sound).setVolume(anyLong(), eq(0f));

        audioManager.setSoundsEnabled(true);
        verify(sound).setVolume(anyLong(), eq(0.6f));
    }

    // Music Management Tests

    @Test
    void testPlayMusicWhenEnabled() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.setMusicEnabled(true);
        audioManager.setMusicVolume(0.5f);

        audioManager.playMusic(music, true);

        verify(music).setLooping(true);
        verify(music).setVolume(0.5f);
        verify(music).play();
    }

    @Test
    void testPlayMusicWhenDisabled() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.setMusicEnabled(false);

        audioManager.playMusic(music, false);

        verify(music).setLooping(false);
        verify(music).setVolume(0);
        verify(music).play();
    }

    @Test
    void testStopMusic() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.playMusic(music, true);
        audioManager.stopMusic();

        verify(music).stop();
        verify(music).dispose();
    }

    @Test
    void testSetMusicVolumeWhenEnabled() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.setMusicEnabled(true);
        audioManager.playMusic(music, false);

        audioManager.setMusicVolume(0.8f);

        verify(music).setVolume(0.8f);
    }

    @Test
    void testSetMusicVolumeWhenDisabled() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.setMusicEnabled(false);
        audioManager.playMusic(music, false);

        reset(music);

        audioManager.setMusicVolume(0.8f);

        verify(music, never()).setVolume(anyFloat());
    }


    @Test
    void testGetMusicVolume() {
        var audioManager = new AudioManager();
        assertThat(audioManager.getMusicVolume()).isEqualTo(DEFAULT_MUSIC_VOLUME);
    }

    @Test
    void testIsMusicEnabled() {
        var audioManager = new AudioManager();
        assertThat(audioManager.isMusicEnabled()).isEqualTo(DEFAULT_MUSIC_ENABLED);
    }

    @Test
    void testSetMusicEnabled() {
        var audioManager = new AudioManager();
        audioManager.setMusicEnabled(true);
        assertThat(audioManager.isMusicEnabled()).isTrue();

        audioManager.setMusicEnabled(false);
        assertThat(audioManager.isMusicEnabled()).isFalse();
    }

    @Test
    void testSetMusicEnabledWhenMusicIsPlaying() {
        var audioManager = new AudioManager();
        Music music = mock(Music.class);
        audioManager.playMusic(music, false);

        verify(music).play();
        verify(music).setVolume(DEFAULT_MUSIC_VOLUME);

        audioManager.setMusicEnabled(false);
        verify(music).setVolume(0);

        audioManager.setMusicEnabled(true);
        verify(music, times(2)).setVolume(DEFAULT_MUSIC_VOLUME);
    }

    @Test
    void testPlayMusicWhenAnotherMusicIsPlaying() {
        var audioManager = new AudioManager();
        Music oldMusic = mock(Music.class);
        Music newMusic = mock(Music.class);

        audioManager.playMusic(oldMusic, false);

        verify(oldMusic).play();
        verify(oldMusic).setLooping(false);
        verify(oldMusic).setVolume(DEFAULT_MUSIC_VOLUME);

        audioManager.playMusic(newMusic, true);

        verify(oldMusic).stop();
        verify(oldMusic).dispose();

        verify(newMusic).setLooping(true);
        verify(newMusic).setVolume(DEFAULT_MUSIC_VOLUME);
        verify(newMusic).play();
    }

}
