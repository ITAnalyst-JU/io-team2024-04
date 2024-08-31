package core.audio;

public class AudioManagerFactory {
    private static AudioManager audioManager;

    public static AudioManager getAudioManager() {
        if (audioManager == null) {
            audioManager = new AudioManager();
        }
        return audioManager;
    }
}
