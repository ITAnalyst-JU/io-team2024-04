package core.preferences;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalPreferencesInteractorFactoryTest {
    @Test
    void testGetPreferencesInteractor() {
        InternalPreferencesInteractorFactory factory = new InternalPreferencesInteractorFactory();
        IInternalPreferencesInteractor interactor = factory.getPreferencesInteractor();
        assertThat(interactor).isNotNull();
    }
}
