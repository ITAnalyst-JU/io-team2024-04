package core.preferences;

import org.junit.jupiter.api.Test;

public class WindowModeTest {
    @Test
    public void testToString() {
        WindowMode windowMode = new WindowMode(800, 600);
        String result = windowMode.toString();
        assert result.equals("800x600");
    }
    @Test
    public void testGetters() {
        WindowMode windowMode = new WindowMode(800, 600);
        assert windowMode.width() == 800;
        assert windowMode.height() == 600;
    }
}
