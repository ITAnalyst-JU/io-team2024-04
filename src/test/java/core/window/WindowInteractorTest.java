package core.window;

import core.preferences.IInternalWindowPreferencesInteractor;
import core.preferences.WindowMode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WindowInteractorTest {
    @Test
    public void testIsFullscreen() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        Mockito.when(internalInteractor.isFullscreen()).thenReturn(true);

        var res = interactor.isFullscreen();

        Assertions.assertThat(res).isTrue();
        Mockito.verify(internalInteractor).isFullscreen();
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testSetFullscreen() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);

        interactor.setFullscreen(true);

        Mockito.verify(internalInteractor).setFullscreen(true);
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testGetWindowMode() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        Mockito.when(internalInteractor.getWindowWidth()).thenReturn(76);

        var res = interactor.getWindowMode();

        Assertions.assertThat(res.width()).isEqualTo(76);
        Mockito.verify(internalInteractor).getWindowWidth();
        Mockito.verify(internalInteractor).getWindowHeight();
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testSetWindowMode() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        var windowMode = Mockito.mock(WindowMode.class);
        Mockito.when(windowMode.width()).thenReturn(100);
        Mockito.when(windowMode.height()).thenReturn(200);

        interactor.setWindowMode(windowMode);

        Mockito.verify(internalInteractor).setWindowSize(100, 200);
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testGetFps() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        Mockito.when(internalInteractor.getFps()).thenReturn(41);

        var res = interactor.getFps();

        Assertions.assertThat(res).isEqualTo(41);
        Mockito.verify(internalInteractor).getFps();
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testSetFps() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);

        interactor.setFps(43);

        Mockito.verify(internalInteractor).setFps(43);
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testIsVSync() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        Mockito.when(internalInteractor.isVSync()).thenReturn(true);

        var res = interactor.isVSync();

        Assertions.assertThat(res).isTrue();
        Mockito.verify(internalInteractor).isVSync();
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testSetVSync() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);

        interactor.setVSync(true);

        Mockito.verify(internalInteractor).setVSync(true);
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testLoadPreferences() {
        var internalInteractor = Mockito.mock(IInternalWindowPreferencesInteractor.class);
        var interactor = new WindowInteractor(internalInteractor);
        Mockito.when(internalInteractor.isFullscreen()).thenReturn(true);
        Mockito.when(internalInteractor.getWindowWidth()).thenReturn(76);
        Mockito.when(internalInteractor.getWindowHeight()).thenReturn(54);
        Mockito.when(internalInteractor.getFps()).thenReturn(41);

        interactor.loadPreferences();

        Mockito.verify(internalInteractor).isFullscreen();
        Mockito.verify(internalInteractor).setFullscreen(true);
        Mockito.verify(internalInteractor).getWindowWidth();
        Mockito.verify(internalInteractor).getWindowHeight();
        Mockito.verify(internalInteractor).setWindowSize(76, 54);
        Mockito.verify(internalInteractor).getFps();
        Mockito.verify(internalInteractor).setFps(41);
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }
}
