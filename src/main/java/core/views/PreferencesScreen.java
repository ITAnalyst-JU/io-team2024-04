package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.music.SoundInteractor;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage, SoundInteractor soundInteractor) {
        super(stage);

        addSlider("Music Volume", 0, 1, 0.01f, soundInteractor.getMusicVolume(), () -> {
            float newVolume = getSliderValue(0);
            soundInteractor.setMusicVolume(newVolume);
        });

        addCheckbox("Enable Music", soundInteractor.getMusicVolume() > 0, () -> {
            boolean isEnabled = getCheckboxState(0);
            soundInteractor.setMusicVolume(isEnabled ? soundInteractor.getMusicVolume() : 0);
        });

        addSlider("Sound Effects Volume", 0, 1, 0.01f, soundInteractor.getSoundsVolume(), () -> {
            float newVolume = getSliderValue(1);
            soundInteractor.setSoundsVolume(newVolume);
        });

        addCheckbox("Enable Sound Effects", soundInteractor.getSoundsVolume() > 0, () -> {
            boolean isEnabled = getCheckboxState(1);
            soundInteractor.setSoundsVolume(isEnabled ? soundInteractor.getSoundsVolume() : 0);
        });

        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));
    }
}
