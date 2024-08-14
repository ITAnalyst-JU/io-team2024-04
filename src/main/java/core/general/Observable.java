package core.general;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Observable<T> {
    protected final List<T> observers = new ArrayList<>();

    public void addObserver(T observer) { observers.add(observer); }
    public void removeObserver(T observer) { observers.remove(observer); }
    protected void notifyObservers(Consumer<T> action) { observers.forEach(action); }
}
