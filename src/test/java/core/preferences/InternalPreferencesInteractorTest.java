package core.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Version;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class InternalPreferencesInteractorTest {
    @Test
    public void testGetMusicVolume() {
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.getMusicVolume()).thenReturn(0.75f);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

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
        when(userPreferences.isMusicEnabled()).thenReturn(true);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

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
        when(userPreferences.getSoundVolume()).thenReturn(0.75f);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

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
        when(userPreferences.isSoundEffectsEnabled()).thenReturn(true);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

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
        when(userPreferences.isFullscreen()).thenReturn(true);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        boolean isFullscreen = preferencesInteractor.isFullscreen();

        assertTrue(isFullscreen);
        verify(userPreferences).isFullscreen();
    }

    @Test
    public void testSetFullscreenFalse() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.getWindowWidth()).thenReturn(420);
        when(userPreferences.getWindowHeight()).thenReturn(1337);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setFullscreen(false);

        verify(userPreferences).setFullscreen(false);
        verify(Gdx.graphics).setWindowedMode(420, 1337);
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void testSetFullscreenTrue() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        when(Gdx.graphics.getDisplayMode()).thenReturn(mock(Graphics.DisplayMode.class));
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setFullscreen(true);

        verify(userPreferences).setFullscreen(true);
        verify(Gdx.graphics).setFullscreenMode(any());
        verify(Gdx.graphics).getDisplayMode();
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void getWindowWidth() {
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.getWindowWidth()).thenReturn(420);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        int windowWidth = preferencesInteractor.getWindowWidth();

        assertEquals(420, windowWidth);
        verify(userPreferences).getWindowWidth();
    }

    @Test
    public void getWindowHeight() {
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.getWindowHeight()).thenReturn(1337);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        int windowHeight = preferencesInteractor.getWindowHeight();

        assertEquals(1337, windowHeight);
        verify(userPreferences).getWindowHeight();
    }

    @Test
    public void testSetWindowSizeFullscreen() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.isFullscreen()).thenReturn(true);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setWindowSize(1920, 1080);

        verify(userPreferences).setWindowSize(1920, 1080);
        verifyNoInteractions(Gdx.graphics);
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void testSetWindowSizeWindowed() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.isFullscreen()).thenReturn(false);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setWindowSize(1920, 1080);

        verify(userPreferences).setWindowSize(1920, 1080);
        verify(Gdx.graphics).setWindowedMode(1920, 1080);
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void testGetFps() {
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.getFps()).thenReturn(60);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        int fps = preferencesInteractor.getFps();

        assertEquals(60, fps);
        verify(userPreferences).getFps();
    }

    @Test
    public void testSetFps() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setFps(30);

        verify(userPreferences).setFps(30);
        verify(Gdx.graphics).setForegroundFPS(30);
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void testSetVSync() {
        Gdx.graphics = mock(com.badlogic.gdx.Graphics.class);
        var userPreferences = mock(UserPreferences.class);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

        preferencesInteractor.setVSync(true);

        verify(userPreferences).setVSync(true);
        verify(Gdx.graphics).setVSync(true);
        verifyNoMoreInteractions(Gdx.graphics);
    }

    @Test
    public void testIsVSync() {
        var userPreferences = mock(UserPreferences.class);
        when(userPreferences.isVSync()).thenReturn(true);
        var preferencesInteractor = new InternalPreferencesInteractor(userPreferences);

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
