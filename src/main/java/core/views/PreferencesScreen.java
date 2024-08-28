package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.audio.AudioInteractor;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage, AudioInteractor audioInteractor) {
        super(stage);

        addSlider("Music Volume", 0, 1, 0.01f, audioInteractor.getMusicVolume(), () -> {
            float newVolume = getSliderValue(0);
            audioInteractor.setMusicVolume(newVolume);
        });

        addCheckbox("Enable Music", audioInteractor.isMusicEnabled(), () -> {
            boolean isEnabled = getCheckboxState(0);
            audioInteractor.setMusicEnabled(isEnabled);
        });

        addSlider("Sound Effects Volume", 0, 1, 0.01f, audioInteractor.getSoundsVolume(), () -> {
            float newVolume = getSliderValue(1);
            audioInteractor.setSoundsVolume(newVolume);
        });

        addCheckbox("Enable Sounds Effects", audioInteractor.areSoundsEnabled(), () -> {
            boolean isEnabled = getCheckboxState(1);
            audioInteractor.setSoundsEnabled(isEnabled);
        });

        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));
    }
}
