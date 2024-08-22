package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import desktop.preferences.PreferencesOrchestrator;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage, PreferencesOrchestrator preferencesOrchestrator) {
        super(stage);

        addSlider("Music Volume", 0, 100, 1, preferencesOrchestrator.getMusicVolume(), () -> {
            float newVolume = getSliderValue(0);
            preferencesOrchestrator.setMusicVolume(newVolume);
        });

        addCheckbox("Enable Music", preferencesOrchestrator.isMusicEnabled(), () -> {
            boolean isEnabled = getCheckboxState(0);
            preferencesOrchestrator.setMusicEnabled(isEnabled);
        });

        addSlider("Sound Effects Volume", 0, 100, 1, preferencesOrchestrator.getSoundVolume(), () -> {
            float newVolume = getSliderValue(1);
            preferencesOrchestrator.setSoundVolume(newVolume);
        });

        addCheckbox("Enable Sound Effects", preferencesOrchestrator.isSoundEffectsEnabled(), () -> {
            boolean isEnabled = getCheckboxState(1);
            preferencesOrchestrator.setSoundEffectsEnabled(isEnabled);
        });

        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));
    }
}
