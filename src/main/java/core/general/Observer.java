package core.general;

public interface Observer<T> {
    void respondToEvent(T param);
}
