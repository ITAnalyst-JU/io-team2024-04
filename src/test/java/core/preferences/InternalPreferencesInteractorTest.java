package core.preferences;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: add tests to setters
public class InternalPreferencesInteractorTest {
    @Test
    public void testGetMusicVolume() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getMusicVolume()).thenReturn(0.75f);

        float volume = preferencesInteractor.getMusicVolume();

        assertEquals(0.75f, volume, 0.0f);
        verify(userPreferences).getMusicVolume();
    }

    @Test
    public void testSetMusicVolume() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setMusicVolume(0.5f);

        verify(userPreferences).setMusicVolume(0.5f);
    }

    @Test
    public void testIsMusicEnabled() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.isMusicEnabled()).thenReturn(true);

        boolean enabled = preferencesInteractor.isMusicEnabled();

        assertTrue(enabled);
        verify(userPreferences).isMusicEnabled();
    }

    @Test
    public void testSetMusicEnabled() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setMusicEnabled(false);

        verify(userPreferences).setMusicEnabled(false);
    }

    @Test
    public void testGetSoundVolume() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getSoundVolume()).thenReturn(0.75f);

        float volume = preferencesInteractor.getSoundVolume();

        assertEquals(0.75f, volume, 0.0f);
        verify(userPreferences).getSoundVolume();
    }

    @Test
    public void testSetSoundVolume() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setSoundVolume(0.5f);

        verify(userPreferences).setSoundVolume(0.5f);
    }

    @Test
    public void testIsSoundEnabled() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.isSoundEffectsEnabled()).thenReturn(true);

        boolean enabled = preferencesInteractor.isSoundEffectsEnabled();

        assertTrue(enabled);
        verify(userPreferences).isSoundEffectsEnabled();
    }

    @Test
    public void testSetSoundEnabled() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setSoundEffectsEnabled(false);

        verify(userPreferences).setSoundEffectsEnabled(false);
    }

    @Test
    public void testIsFullscreen() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.isFullscreen()).thenReturn(true);

        boolean isFullscreen = preferencesInteractor.isFullscreen();

        assertTrue(isFullscreen);
        verify(userPreferences).isFullscreen();
    }

    @Test
    public void getWindowWidth() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getWindowWidth()).thenReturn(420);

        int windowWidth = preferencesInteractor.getWindowWidth();

        assertEquals(420, windowWidth);
        verify(userPreferences).getWindowWidth();
    }

    @Test
    public void getWindowHeight() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getWindowHeight()).thenReturn(1337);

        int windowHeight = preferencesInteractor.getWindowHeight();

        assertEquals(1337, windowHeight);
        verify(userPreferences).getWindowHeight();
    }

    @Test
    public void testGetFps() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getFps()).thenReturn(60);

        int fps = preferencesInteractor.getFps();

        assertEquals(60, fps);
        verify(userPreferences).getFps();
    }

    @Test
    public void testIsVSync() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.isVSync()).thenReturn(true);

        boolean vsync = preferencesInteractor.isVSync();

        assertTrue(vsync);
        verify(userPreferences).isVSync();
    }

    @Test
    public void testSetUserName() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setUserName("player1");

        verify(userPreferences).setUserName("player1");
    }

    @Test
    public void testGetUserName() {
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);
        when(userPreferences.getUserName()).thenReturn("player1");

        String username = preferencesInteractor.getUserName();

        assertEquals("player1", username);
        verify(userPreferences).getUserName();
    }
}
