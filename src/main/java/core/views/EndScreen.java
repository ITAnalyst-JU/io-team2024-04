package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import core.assets.IAssetManagerFactory;
import core.network.HighScoreNetworkInteractor;

// NOTE: UNTESTABLE
public class EndScreen extends UIScreen {
    public EndScreen(Stage stage, IAssetManagerFactory assetManagerFactory, HighScoreNetworkInteractor highScoreInteractor, int levelNumber) {
        super(stage, assetManagerFactory);

        setBackgroundImage("ui/background/gradle.jpg");

        table.top().padTop(50);
        table.add(createLabelWithBackground("Congratulations, you completed the level!")).expandX().padBottom(50);
        table.row();

        table.add(createButton("Play Again", () -> notifyOrchestrator(ScreenEnum.GAME))).expandX().padTop(20);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();

        Table highScoreTable = new Table();
        highScoreTable.setBackground(skin.getDrawable("round-gray"));
        table.add(highScoreTable).expand().center().pad(10);
        generateHighScoresTable(highScoreTable,highScoreInteractor, levelNumber, 5);
    }
}
