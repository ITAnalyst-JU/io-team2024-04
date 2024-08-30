package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;

import java.util.List;

public class EndScreen extends UIScreen {
    private HighScoreInteractor highScoreInteractor;
    private Table highScoreTable;

    public EndScreen(Stage stage, HighScoreInteractor highScoreInteractor) {
        super(stage);
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
