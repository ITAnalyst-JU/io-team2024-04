package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.IAssetManagerFactory;
import core.network.HighScoreNetworkInteractor;

// NOTE: UNTESTABLE
public class LeaderboardScreen extends UIScreen {
    private final HighScoreNetworkInteractor highScoreInteractor;
    private final Table highScoreTable;
    private final Label levelLabel;

    public LeaderboardScreen(Stage stage, IAssetManagerFactory assetManagerFactory, HighScoreNetworkInteractor highScoreInteractor) {
        super(stage, assetManagerFactory);
        this.highScoreInteractor = highScoreInteractor;

        setBackgroundImage("ui/background/preview.png");

        Table greyBackground = new Table();
        greyBackground.setBackground(skin.getDrawable("round-gray"));
        levelLabel = createLabel("Level: 1");
        greyBackground.add(levelLabel);

        Table buttonTable = new Table();
        buttonTable.top().center();
        buttonTable.add(createButton("Level 1", () -> updateHighScoresForLevel(1))).pad(10);
        buttonTable.add(createButton("Level 2", () -> updateHighScoresForLevel(2))).pad(10);
        buttonTable.add(createButton("Level 3", () -> updateHighScoresForLevel(3))).pad(10);

        table.top().left().pad(10);
        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().align(1).right();
        table.row();
        table.add(buttonTable).expandX().center().padTop(20).row();
        table.add(greyBackground);
        table.row();

        highScoreTable = new Table();
        highScoreTable.setBackground(skin.getDrawable("round-gray"));
        table.add(highScoreTable).expandX().padTop(10).row();

        generateHighScoresTable(highScoreTable, highScoreInteractor, 1, 5);
    }

    private void updateHighScoresForLevel(int levelId) {
        levelLabel.setText("Level: " + levelId);
        generateHighScoresTable(highScoreTable, highScoreInteractor, levelId, 5);
    }
}
