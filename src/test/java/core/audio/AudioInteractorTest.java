package core.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import core.preferences.IInternalAudioPreferencesInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class AudioInteractorTest {
    private AudioControl soundControl;
    private IInternalAudioPreferencesInteractor internalPreferencesInteractor;
    private AudioInteractor interactor;

    @BeforeEach
    void setUp() {
        soundControl = mock(AudioControl.class);
        internalPreferencesInteractor = mock(IInternalAudioPreferencesInteractor.class);
        interactor = new AudioInteractor(soundControl, internalPreferencesInteractor);
    }

    @Test
    void testLoadPreferences() {
        when(internalPreferencesInteractor.getMusicVolume()).thenReturn(0.5f);
        when(internalPreferencesInteractor.isMusicEnabled()).thenReturn(true);
        when(internalPreferencesInteractor.getSoundVolume()).thenReturn(0.7f);
        when(internalPreferencesInteractor.isSoundEffectsEnabled()).thenReturn(false);

        interactor.loadPreferences();

        verify(soundControl).setMusicVolume(0.5f);
        verify(soundControl).setMusicEnabled(true);
        verify(soundControl).setSoundsVolume(0.7f);
        verify(soundControl).setSoundsEnabled(false);

        verify(internalPreferencesInteractor).getMusicVolume();
        verify(internalPreferencesInteractor).isMusicEnabled();
        verify(internalPreferencesInteractor).getSoundVolume();
        verify(internalPreferencesInteractor).isSoundEffectsEnabled();
    }

    @Test
    void testPlaySoundEffect() {
        Sound sound = mock(Sound.class);

        interactor.playSoundEffect(sound);

        verify(soundControl).playSound(sound);
    }

    @Test
    void testStopAllSoundEffects() {
        interactor.stopAllSoundEffects();

        verify(soundControl).stopAllSounds();
    }

    @Test
    void testSetSoundsVolume() {
        interactor.setSoundsVolume(0.8f);

        verify(soundControl).setSoundsVolume(0.8f);
        verify(internalPreferencesInteractor).setSoundVolume(0.8f);
    }

    @Test
    void testGetSoundsVolume() {
        when(soundControl.getSoundsVolume()).thenReturn(0.6f);

        float volume = interactor.getSoundsVolume();

        assertThat(volume).isEqualTo(0.6f);
        verify(soundControl).getSoundsVolume();
    }

    @Test
    void testPlayBackgroundMusic() {
        Music music = mock(Music.class);

        interactor.playBackgroundMusic(music, true);

        verify(soundControl).playMusic(music, true);
    }

    @Test
    void testStopBackgroundMusic() {
        interactor.stopBackgroundMusic();

        verify(soundControl).stopMusic();
    }

    @Test
    void testSetMusicVolume() {
        interactor.setMusicVolume(0.8f);

        verify(soundControl).setMusicVolume(0.8f);
        verify(internalPreferencesInteractor).setMusicVolume(0.8f);
    }

    @Test
    void testGetMusicVolume() {
        when(soundControl.getMusicVolume()).thenReturn(0.4f);

        float volume = interactor.getMusicVolume();

        assertThat(volume).isEqualTo(0.4f);
        verify(soundControl).getMusicVolume();
    }


    @Test
    void testAreSoundsEnabled() {
        when(soundControl.isSoundsEnabled()).thenReturn(true);

        boolean areSoundsEnabled = interactor.areSoundsEnabled();

        assertThat(areSoundsEnabled).isTrue();
        verify(soundControl).isSoundsEnabled();
    }

    @Test
    void testSetSoundsEnabled() {
        interactor.setSoundsEnabled(true);

        verify(soundControl).setSoundsEnabled(true);
        verify(internalPreferencesInteractor).setSoundEffectsEnabled(true);
    }

    @Test
    void testIsMusicEnabled() {
        when(soundControl.isMusicEnabled()).thenReturn(false);

        boolean isMusicEnabled = interactor.isMusicEnabled();

        assertThat(isMusicEnabled).isFalse();
        verify(soundControl).isMusicEnabled();
    }


    @Test
    void testSetMusicEnabled() {
        interactor.setMusicEnabled(false);

        verify(soundControl).setMusicEnabled(false);
        verify(internalPreferencesInteractor).setMusicEnabled(false);
    }

    @Test
    void testToggleSoundsEnabled() {
        interactor.setSoundsEnabled(true);
        verify(soundControl).setSoundsEnabled(true);

        interactor.setSoundsEnabled(false);
        verify(soundControl).setSoundsEnabled(false);

        interactor.setSoundsEnabled(true);
        verify(soundControl, times(2)).setSoundsEnabled(true);
    }

    @Test
    void testToggleMusicEnabled() {
        interactor.setMusicEnabled(true);
        verify(soundControl).setMusicEnabled(true);

        interactor.setMusicEnabled(false);
        verify(soundControl).setMusicEnabled(false);

        interactor.setMusicEnabled(true);
        verify(soundControl, times(2)).setMusicEnabled(true);
    }

    @Test
    void testPlaySoundEffectWithNull() {
        interactor.playSoundEffect(null);
        verify(soundControl, never()).playSound(any(Sound.class));
    }

    @Test
    void testPlayBackgroundMusicWithNull() {
        interactor.playBackgroundMusic(null, false);
        verify(soundControl, never()).playMusic(any(Music.class), anyBoolean());
    }
}
