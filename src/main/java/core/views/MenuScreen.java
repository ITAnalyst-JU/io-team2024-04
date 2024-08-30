package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import core.audio.AudioInteractor;
import core.window.WindowInteractor;

public class MenuScreen extends UIScreen {
    public MenuScreen(Stage stage, AudioInteractor audioInteractor, WindowInteractor windowInteractor) {
        super(stage);
        setBackgroundImage("ui/background/gradle.jpg");

        // TODO: probably should be in show() method
        audioInteractor.loadPreferences();
        windowInteractor.loadPreferences();
        audioInteractor.playBackgroundMusic("audio/music/epic_free_music.mp3", true);

        Table greyBackground = new Table();
        greyBackground.setBackground(skin.getDrawable("round-gray"));
        Label welcomeLabel = createLabel("Wake the fuck up, Anon! Your build.gradle needs fixing!");
        greyBackground.add(welcomeLabel);
        table.add(greyBackground).expandX().padBottom(20).center();
        table.row();

        table.add(createButton("Start game", () -> notifyOrchestrator(ScreenEnum.LEVELSELECTION))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Preferences", () -> notifyOrchestrator(ScreenEnum.PREFERENCES))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Custom action", () -> System.out.println("Gdzie jest Nero"))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Exit", Gdx.app::exit)).expandX().padBottom(10);
        table.row();

        TextField nameField = createTextField("Enter your name here");
        TextButton updateButton = createButton("Update", () -> {
            String name = nameField.getText();
            if (name.isEmpty()) {
                name = "Anon";
            }
            welcomeLabel.setText("Wake the fuck up, " + name + "! Your build.gradle needs fixing!");
        });

        Table inputTable = new Table();
        inputTable.add(nameField).width(300).padRight(10);
        inputTable.add(updateButton);
        table.add(inputTable).padBottom(10);
        table.row();
    }
}
