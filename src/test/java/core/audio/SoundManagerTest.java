package core.audio;

import com.badlogic.gdx.audio.Sound;
import org.junit.jupiter.api.Test;

import static core.general.Constants.Preferences.DEFAULT_SOUND_ENABLED;
import static core.general.Constants.Preferences.DEFAULT_SOUND_VOLUME;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class SoundManagerTest {
    @Test
    void testPlaySoundWhenEnabled() {
        var soundManager = new SoundManager();
        var sound = mock(Sound.class);
        soundManager.setSoundsEnabled(true);
        soundManager.setVolume(0.5f);

        soundManager.playSound(sound);

        verify(sound).play(0.5f);
    }

    @Test
    void testSoundWhenDisabled() {
        var soundManager = new SoundManager();
        var sound = mock(Sound.class);
        soundManager.setSoundsEnabled(false);

        soundManager.playSound(sound);

        verify(sound).play(0);
    }

    @Test
    void testStopAllSounds() {
        var soundManager = new SoundManager();
        Sound sound1 = mock(Sound.class);
        Sound sound2 = mock(Sound.class);
        when(sound1.play(anyFloat())).thenReturn(1L);
        when(sound2.play(anyFloat())).thenReturn(2L);

        soundManager.setSoundsEnabled(true);
        soundManager.playSound(sound1);
        soundManager.playSound(sound2);

        soundManager.stopAllSounds();

        verify(sound1).stop();
        verify(sound1).dispose();
        verify(sound2).stop();
        verify(sound2).dispose();
        assertThat(soundManager.getPlayingSounds()).isEmpty();
    }

    @Test
    void testSetVolumeWhenEnabled() {
        var soundManager = new SoundManager();
        var sound = mock(Sound.class);
        soundManager.setSoundsEnabled(true);
        soundManager.playSound(sound);

        soundManager.setVolume(0.8f);

        verify(sound).setVolume(anyLong(), eq(0.8f));
    }

    @Test
    void testSetVolumeWhenDisabled() {
        var soundManager = new SoundManager();
        var sound = mock(Sound.class);
        soundManager.setSoundsEnabled(false);
        soundManager.playSound(sound);

        soundManager.setVolume(0.8f);

        verify(sound, never()).setVolume(anyLong(), anyFloat());
    }

    @Test
    void testToggleSoundsEnabled() {
        var soundManager = new SoundManager();
        soundManager.setSoundsEnabled(true);
        soundManager.setVolume(0.7f);

        soundManager.setSoundsEnabled(false);
        assertThat(soundManager.areSoundsEnabled()).isFalse();
        assertThat(soundManager.getVolume()).isEqualTo(0.0f);

        soundManager.setSoundsEnabled(true);
        assertThat(soundManager.areSoundsEnabled()).isTrue();
        assertThat(soundManager.getVolume()).isEqualTo(0.7f);
    }

    @Test
    void testGetVolume() {
        var soundManager = new SoundManager();

        assertThat(soundManager.getVolume()).isEqualTo(DEFAULT_SOUND_VOLUME);
    }

    @Test
    void testAreSoundsEnabled() {
        var soundManager = new SoundManager();

        assertThat(soundManager.areSoundsEnabled()).isEqualTo(DEFAULT_SOUND_ENABLED);
    }

    @Test
    void testSetSoundsEnabledWhenTrue() {
        var soundManager = new SoundManager();
        soundManager.setSoundsEnabled(true);

        assertThat(soundManager.areSoundsEnabled()).isTrue();
        assertThat(soundManager.getVolume()).isEqualTo(DEFAULT_SOUND_VOLUME);
    }

    @Test
    void testSetSoundsEnabledWhenFalse() {
        var soundManager = new SoundManager();
        soundManager.setSoundsEnabled(false);

        assertThat(soundManager.areSoundsEnabled()).isFalse();
        assertThat(soundManager.getVolume()).isEqualTo(0.0f);
    }
}
