package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import core.audio.AudioInteractor;

public class MenuScreen extends UIScreen {
    public MenuScreen(Stage stage, AudioInteractor audioInteractor) {
        super(stage);

        audioInteractor.loadPreferences();
        audioInteractor.playBackgroundMusic("music/epic_free_music.mp3", true);

        Label titleLabel = new Label("Gradle Demon Adventures", skin, "default");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        Label welcomeLabel = new Label("Wake the fuck up, Anon! Your build.gradle needs fixing!", skin);
        table.add(welcomeLabel).expandX().padBottom(10);
        table.row();

        addButton("Start game", () -> notifyOrchestrator(ScreenEnum.LEVELSELECTION));
        addButton("Preferences", () -> notifyOrchestrator(ScreenEnum.PREFERENCES));
        addButton("Custom action", () -> System.out.println("Gdzie jest Nero"));
        addButton("Exit", Gdx.app::exit);

        addTextFieldWithButton(
                "Enter your name here",
                "Update",
                textField -> {
                    String name = textField.getText();
                    if (name.isEmpty()) {
                        name = "Anon";
                    }
                    welcomeLabel.setText("Wake the fuck up, " + name + "! Your build.gradle needs fixing!");
                }
        );
    }
}
