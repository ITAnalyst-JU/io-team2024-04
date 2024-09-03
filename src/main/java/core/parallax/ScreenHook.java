package core.parallax;

// (0, 0) is at the center of the screen
public record ScreenHook (
    int centerX,
    int centerY,
    int width,
    int height
) {
}
