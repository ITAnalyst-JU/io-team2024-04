package core.views;

import core.preferences.InternalPreferencesInteractorFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SupremeInteractorFactoryTest {
    @Test
    public void testGetHighScoreInteractor() {
        var supremeInteractorFactory = new SupremeInteractorFactory(null);
        assertThat(supremeInteractorFactory.getHighScoreInteractor()).isNotNull();
    }

    @Test
    public void testGetAudioInteractor() {
        var internalPreferencesInteractorFactory = mock(InternalPreferencesInteractorFactory.class);
        when(internalPreferencesInteractorFactory.getPreferencesInteractor()).thenReturn(null);
        var supremeInteractorFactory = new SupremeInteractorFactory(internalPreferencesInteractorFactory);
        assertThat(supremeInteractorFactory.getAudioInteractor()).isNotNull();
    }

    @Test
    public void testGetWindowInteractor() {
        var internalPreferencesInteractorFactory = mock(InternalPreferencesInteractorFactory.class);
        when(internalPreferencesInteractorFactory.getPreferencesInteractor()).thenReturn(null);
        var supremeInteractorFactory = new SupremeInteractorFactory(internalPreferencesInteractorFactory);
        assertThat(supremeInteractorFactory.getWindowInteractor()).isNotNull();
    }

    @Test
    public void testGetUserInteractor() {
        var internalPreferencesInteractorFactory = mock(InternalPreferencesInteractorFactory.class);
        when(internalPreferencesInteractorFactory.getPreferencesInteractor()).thenReturn(null);
        var supremeInteractorFactory = new SupremeInteractorFactory(internalPreferencesInteractorFactory);
        assertThat(supremeInteractorFactory.getUserInteractor()).isNotNull();
    }
}
