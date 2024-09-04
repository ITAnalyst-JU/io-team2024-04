package core.preferences;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static core.general.Constants.Preferences.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserPreferencesTest {


    @BeforeAll
    static public void setUp() {
        Gdx.app = mock(Application.class);
    }

    @Test
    public void testIsSoundEffectsEnabled() {
        var preferences = mock(Preferences.class);
        when(preferences.getBoolean("sound.enabled", DEFAULT_SOUND_ENABLED)).thenReturn(true);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertTrue(userPreferences.isSoundEffectsEnabled());
    }

    @Test
    public void testSetSoundEffectsEnabled() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setSoundEffectsEnabled(true);
        verify(preferences).putBoolean("sound.enabled", true);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testIsMusicEnabled() {
        var preferences = mock(Preferences.class);
        when(preferences.getBoolean("music.enabled", DEFAULT_MUSIC_ENABLED)).thenReturn(true);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertTrue(userPreferences.isMusicEnabled());
    }

    @Test
    public void testSetMusicEnabled() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setMusicEnabled(true);
        verify(preferences).putBoolean("music.enabled", true);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testGetMusicVolume() {
        var preferences = mock(Preferences.class);
        when(preferences.getFloat("volume", DEFAULT_MUSIC_VOLUME)).thenReturn(0.5f);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals(0.5f, userPreferences.getMusicVolume());
    }

    @Test
    public void testSetMusicVolume() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setMusicVolume(0.5f);
        verify(preferences).putFloat("volume", 0.5f);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testGetSoundVolume() {
        var preferences = mock(Preferences.class);
        when(preferences.getFloat("sound", DEFAULT_SOUND_VOLUME)).thenReturn(0.5f);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals(0.5f, userPreferences.getSoundVolume());
    }

    @Test
    public void testSetSoundVolume() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setSoundVolume(0.5f);
        verify(preferences).putFloat("sound", 0.5f);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testIsFullscreen() {
        var preferences = mock(Preferences.class);
        when(preferences.getBoolean("fullscreen.enabled", DEFAULT_FULLSCREEN_ENABLED)).thenReturn(false);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertFalse(userPreferences.isFullscreen());
    }

    @Test
    public void testSetFullscreen() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setFullscreen(true);
        verify(preferences).putBoolean("fullscreen.enabled", true);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testGetUserName() {
        var preferences = mock(Preferences.class);
        when(preferences.getString("user.name", DEFAULT_USER_NAME)).thenReturn("Anon");
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals("Anon", userPreferences.getUserName());
    }

    @Test
    public void testSetUserName() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setUserName("Thomas Mann");
        verify(preferences).putString("user.name", "Thomas Mann");
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testGetWindowWidth() {
        var preferences = mock(Preferences.class);
        when(preferences.getInteger("window.width", DEFAULT_WINDOW_WIDTH)).thenReturn(800);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals(800, userPreferences.getWindowWidth());
    }

    @Test
    public void testGetWindowHeight() {
        var preferences = mock(Preferences.class);
        when(preferences.getInteger("window.height", DEFAULT_WINDOW_HEIGHT)).thenReturn(600);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals(600, userPreferences.getWindowHeight());
    }

    @Test
    public void testGetFps() {
        var preferences = mock(Preferences.class);
        when(preferences.getInteger("fps", DEFAULT_FPS)).thenReturn(60);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertEquals(60, userPreferences.getFps());
    }

    @Test
    public void testSetFps() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setFps(60);
        verify(preferences).putInteger("fps", 60);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testSetWindowSize() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setWindowSize(800, 600);
        verify(preferences).putInteger("window.width", 800);
        verify(preferences).putInteger("window.height", 600);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }

    @Test
    public void testIsVSync() {
        var preferences = mock(Preferences.class);
        when(preferences.getBoolean("vsync", DEFAULT_VSYNC)).thenReturn(true);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        assertTrue(userPreferences.isVSync());
    }

    @Test
    public void testSetVSync() {
        var preferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("gradle.demon.adventures.preferences")).thenReturn(preferences);
        var userPreferences = new UserPreferences();
        userPreferences.setVSync(true);
        verify(preferences).putBoolean("vsync", true);
        verify(preferences).flush();
        verifyNoMoreInteractions(preferences);
    }
}
