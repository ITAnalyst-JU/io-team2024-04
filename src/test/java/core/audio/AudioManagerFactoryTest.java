package core.audio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AudioManagerFactoryTest {

    @Test
    void testGetAudioManagerSingleton() {
        var instance1 = AudioManagerFactory.getAudioManager();
        var instance2 = AudioManagerFactory.getAudioManager();

        assertThat(instance1).isSameAs(instance2);
    }
}
