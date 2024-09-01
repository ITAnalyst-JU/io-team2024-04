package core.user;

import core.preferences.IInternalUserPreferencesInteractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserInteractorTest {
    @Test
    public void testSetUserName() {
        var internalInteractor = Mockito.mock(IInternalUserPreferencesInteractor.class);
        UserInteractor interactor = new UserInteractor(internalInteractor);
        interactor.setUserName("test4");
        Mockito.verify(internalInteractor, Mockito.times(1)).setUserName("test4");
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }

    @Test
    public void testGetUserName() {
        var internalInteractor = Mockito.mock(IInternalUserPreferencesInteractor.class);
        UserInteractor interactor = new UserInteractor(internalInteractor);
        Mockito.when(interactor.getUserName()).thenReturn("test4");

        String res = interactor.getUserName();

        Assertions.assertThat(res).isEqualTo("test4");
        Mockito.verify(internalInteractor, Mockito.times(1)).getUserName();
        Mockito.verifyNoMoreInteractions(internalInteractor);
    }
}
