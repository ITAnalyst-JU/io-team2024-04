package core.preferences;

public record WindowMode(
        int width,
        int height
) {
    @Override
    public String toString() {
        return width + "x" + height;
    }
}