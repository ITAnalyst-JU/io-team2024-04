package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import core.assets.AssetManagerFactory;
import core.db.app.HighScoreInteractor;

public class EndScreen extends UIScreen {
    private final HighScoreInteractor highScoreInteractor;
    private final Table highScoreTable;

    public EndScreen(Stage stage, AssetManagerFactory assetManagerFactory, HighScoreInteractor highScoreInteractor) {
        super(stage, assetManagerFactory);
        this.highScoreInteractor = highScoreInteractor;

        setBackgroundImage("ui/background/triangles.png");

        Label titleLabel = createLabel("Congratulations, you completed the level!");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        table.add(createButton("Play Again", () -> notifyOrchestrator(ScreenEnum.GAME))).expandX().padTop(20);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();

        highScoreTable = new Table();
        highScoreTable.setBackground(skin.getDrawable("round-gray"));
        table.add(highScoreTable).expand().center().pad(10);
    }

    @Override
    public void show() {
        super.show();
        // TODO pass parameters depending on which level
        generateHighScoresTable(highScoreTable,highScoreInteractor,1, 5);
    }
}
