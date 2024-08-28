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

        addCheckbox("Enable Music", audioInteractor.getMusicVolume() > 0, () -> {
            boolean isDisabled = getCheckboxState(0);
            audioInteractor.setMusicVolume(isDisabled ? audioInteractor.getMusicVolume() : 0);
        });

        addSlider("Sound Effects Volume", 0, 1, 0.01f, audioInteractor.getSoundsVolume(), () -> {
            float newVolume = getSliderValue(1);
            audioInteractor.setSoundsVolume(newVolume);
        });

        addCheckbox("Enable Sounds Effects", audioInteractor.getSoundsVolume() > 0, () -> {
            boolean isEnabled = getCheckboxState(1);
            audioInteractor.setSoundsVolume(isEnabled ? audioInteractor.getSoundsVolume() : 0);
        });

        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));
    }
}
