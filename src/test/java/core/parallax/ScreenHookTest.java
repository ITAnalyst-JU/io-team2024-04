package core.parallax;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ScreenHookTest {
    @Test
    public void testCreateAndValues() {
        ScreenHook screenHook = new ScreenHook(1882, 1929, 7, 1965);
        assertThat(screenHook).isNotNull();
        assertThat(screenHook.centerX()).isEqualTo(1882);
        assertThat(screenHook.centerY()).isEqualTo(1929);
        assertThat(screenHook.width()).isEqualTo(7);
        assertThat(screenHook.height()).isEqualTo(1965);
    }

    @Test
    public void testSetters() {
        ScreenHook screenHook = new ScreenHook(1882, 1929, 7, 1965);
        screenHook.setCenterX(1);
        screenHook.setCenterY(2);
        screenHook.setWidth(3);
        screenHook.setHeight(4);
        assertThat(screenHook.centerX()).isEqualTo(1);
        assertThat(screenHook.centerY()).isEqualTo(2);
        assertThat(screenHook.width()).isEqualTo(3);
        assertThat(screenHook.height()).isEqualTo(4);
    }
}
