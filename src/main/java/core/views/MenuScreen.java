package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Array;
import core.assets.AssetManagerFactory;
import core.assets.IAssetManagerGetter;
import core.audio.AudioInteractor;
import core.parallax.ParallaxBackground;
import core.parallax.ParallaxBackgroundFactory;
import core.parallax.ScreenHook;
import core.user.UserInteractor;
import core.window.WindowInteractor;

public class MenuScreen extends UIScreen {
    private final ParallaxBackground parallaxBackground;
    public MenuScreen(Stage stage, AssetManagerFactory assetManagerFactory, AudioInteractor audioInteractor, WindowInteractor windowInteractor, UserInteractor userInteractor) {
        super(stage, assetManagerFactory);

        IAssetManagerGetter assetManagerGetter = assetManagerFactory.getAssetManagerGetter();
        Pixmap[] parallaxLayersPixmaps = {
                assetManagerGetter.getPixmap("loading_background/sky.png"),
                assetManagerGetter.getPixmap("loading_background/far-clouds.png"),
                assetManagerGetter.getPixmap("loading_background/near-clouds.png"),
                assetManagerGetter.getPixmap("loading_background/far-mountains.png"),
                assetManagerGetter.getPixmap("loading_background/mountains.png"),
                assetManagerGetter.getPixmap("loading_background/trees.png"),
        };
        parallaxBackground = ParallaxBackgroundFactory.createParallaxBackgroundScrolling(parallaxLayersPixmaps, new ScreenHook(0, 0, (int) stage.getWidth(), (int) stage.getHeight()));
        stage.addActor(parallaxBackground);

        audioInteractor.loadPreferences();
        windowInteractor.loadPreferences();
        audioInteractor.playBackgroundMusic(assetManagerGetter.getMusic("audio/music/epic_background.mp3"), true);

        Table greyBackground = new Table();
        greyBackground.setBackground(skin.getDrawable("round-gray"));
        Label welcomeLabel = createLabel("Wake the fuck up, " + userInteractor.getUserName() + "! Your build.gradle needs fixing!");
        greyBackground.add(welcomeLabel);
        table.add(greyBackground).expandX().padBottom(20).center();
        table.row();

        table.add(createButton("Start game", () -> notifyOrchestrator(ScreenEnum.LEVELSELECTION))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Preferences", () -> notifyOrchestrator(ScreenEnum.PREFERENCES))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Leaderboard", () -> notifyOrchestrator(ScreenEnum.LEADERBOARD))).expandX().padBottom(10);
        table.row();
        table.add(createButton("Tutorial", () -> notifyOrchestrator(ScreenEnum.TUTORIAL))).expandX().padBottom(10);
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
            userInteractor.setUserName(name);
        });

        Table inputTable = new Table();
        inputTable.add(nameField).width(300).padRight(10);
        inputTable.add(updateButton);
        table.add(inputTable).padBottom(10);
        table.row();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        parallaxBackground.resizeHook(width, height);
    }
}