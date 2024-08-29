package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;

import core.audio.AudioInteractor;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage, AudioInteractor audioInteractor) {
        super(stage);

        setBackgroundImage("ui/background/triangles.png");

        table.add(createLabel("Music Volume")).expandX().padBottom(10);
        table.row();
        table.add(createSlider(0, 1, 0.01f, audioInteractor.getMusicVolume(), newVolume -> audioInteractor.setMusicVolume(newVolume))).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("Enable Music", audioInteractor.isMusicEnabled(), isEnabled -> audioInteractor.setMusicEnabled(isEnabled))).expandX().padBottom(10);
        table.row();

        table.add(createLabel("Sound Effects Volume")).expandX().padBottom(10);
        table.row();
        table.add(createSlider(0, 1, 0.01f, audioInteractor.getSoundsVolume(), newVolume -> audioInteractor.setSoundsVolume(newVolume))).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("Enable Sounds Effects", audioInteractor.areSoundsEnabled(), isEnabled -> audioInteractor.setSoundsEnabled(isEnabled))).expandX().padBottom(10);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();
    }
}
