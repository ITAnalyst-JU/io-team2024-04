package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import core.assets.AssetManagerFactory;
import core.audio.AudioInteractor;
import core.general.Constants;
import core.preferences.WindowMode;
import core.window.WindowInteractor;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage, AssetManagerFactory assetManagerFactory, AudioInteractor audioInteractor, WindowInteractor windowInteractor) {
        super(stage, assetManagerFactory);

        setBackgroundImage("ui/background/triangles.png");

        table.add(createLabel("Music Volume")).expandX().padBottom(10);
        table.row();
        table.add(createSlider(0, 1, 0.01f, audioInteractor.getMusicVolume(), audioInteractor::setMusicVolume)).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("Enable Music", audioInteractor.isMusicEnabled(), audioInteractor::setMusicEnabled)).expandX().padBottom(10);
        table.row();

        table.add(createLabel("Sound Effects Volume")).expandX().padBottom(10);
        table.row();
        table.add(createSlider(0, 1, 0.01f, audioInteractor.getSoundsVolume(), audioInteractor::setSoundsVolume)).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("Enable Sounds Effects", audioInteractor.areSoundsEnabled(), audioInteractor::setSoundsEnabled)).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("Fullscreen", windowInteractor.isFullscreen(), windowInteractor::setFullscreen)).expandX().padBottom(10);
        table.row();

        table.add(createLabel("Window Mode")).expandX().padBottom(10);
        table.row();
        table.add(createWindowModeSelectBox(windowInteractor)).expandX().padBottom(10);
        table.row();

        table.add(createLabel("FPS")).expandX().padBottom(10);
        table.row();
        table.add(createFpsSelectBox(windowInteractor)).expandX().padBottom(10);
        table.row();

        table.add(createCheckbox("VSync", windowInteractor.isVSync(), windowInteractor::setVSync)).expandX().padBottom(10);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();
    }

    private SelectBox<WindowMode> createWindowModeSelectBox(WindowInteractor windowInteractor) {
        SelectBox<WindowMode> selectBox = new SelectBox<>(skin);
        selectBox.setItems(Constants.Preferences.WINDOW_MODES);
        selectBox.setSelected(windowInteractor.getWindowMode());
        selectBox.addListener(event -> {
            windowInteractor.setWindowMode(selectBox.getSelected());
            return false;
        });
        return selectBox;
    }

    private SelectBox<Integer> createFpsSelectBox(WindowInteractor windowInteractor) {
        SelectBox<Integer> selectBox = new SelectBox<>(skin);
        selectBox.setItems(Constants.Preferences.FPS_OPTIONS);
        selectBox.setSelected(windowInteractor.getFps());
        selectBox.addListener(event -> {
            windowInteractor.setFps(selectBox.getSelected());
            return false;
        });
        return selectBox;
    }
}
