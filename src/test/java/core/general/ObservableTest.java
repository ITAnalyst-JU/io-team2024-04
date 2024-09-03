package core.general;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class ObservableTest {
    private static class DummyClass {}
    private static class DummyObservable extends Observable<Observer<DummyClass>> {}

    @Test
    public void testIsObserversListEmptyByDefault() {
        var observable = new DummyObservable();
        assertThat(observable.observers).isEmpty();
    }

    @Test
    public void testAddObserver() {
        var observable = new DummyObservable();
        var observer = (Observer<DummyClass>) Mockito.mock(Observer.class);
        observable.addObserver(observer);
        assertThat(observable.observers).containsExactly(observer);
    }

    @Test
    public void testRemoveObserver() {
        var observable = new DummyObservable();
        var observer = (Observer<DummyClass>) Mockito.mock(Observer.class);
        observable.addObserver(observer);
        observable.removeObserver(observer);
        assertThat(observable.observers).doesNotContain(observer);
    }



    @Test
    public void testNotifyObservers() {
        var observable = new DummyObservable();
        var observer1 = (Observer<DummyClass>) Mockito.mock(Observer.class);
        var observer2 = (Observer<DummyClass>) Mockito.mock(Observer.class);
        var event = new DummyClass();
        observable.addObserver(observer1);
        observable.addObserver(observer2);
        observable.notifyObservers(observer -> observer.respondToEvent(event));
        verify(observer1, times(1)).respondToEvent(event);
        verify(observer2, times(1)).respondToEvent(event);
        verifyNoMoreInteractions(observer1, observer2);
    }

    @Test
    public void testNotNotifyRemovedObservers() {
        var observable = new DummyObservable();
        var observer1 = (Observer<DummyClass>) Mockito.mock(Observer.class);
        var observer2 = (Observer<DummyClass>) Mockito.mock(Observer.class);
        var event = new DummyClass();
        observable.addObserver(observer1);
        observable.addObserver(observer2);
        observable.removeObserver(observer1);
        observable.notifyObservers(observer -> observer.respondToEvent(event));
        verify(observer1, never()).respondToEvent(event);
        verify(observer2, times(1)).respondToEvent(event);
        verifyNoMoreInteractions(observer1, observer2);
    }
}
