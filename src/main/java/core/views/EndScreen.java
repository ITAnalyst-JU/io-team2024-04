package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;

import java.util.List;

public class EndScreen extends UIScreen {
    private HighScoreInteractor highScoreInteractor;
    private Table highScoreTable;
    private Table parentTable;

    public EndScreen(Stage stage, HighScoreInteractor highScoreInteractor) {
        super(stage);
        this.highScoreInteractor = highScoreInteractor;

        parentTable = new Table();
        highScoreTable = new Table();
        highScoreTable.top();

        parentTable.setFillParent(true);
        stage.addActor(parentTable);

        Label titleLabel = new Label("Congratulations, you completed the level!", skin, "default");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));

        parentTable.add(table).expandX().padBottom(20).row();
        parentTable.add(highScoreTable).expand().fill().pad(10);

        parentTable.row();
        parentTable.add(table).expandX().fillX().padTop(30);
    }

    @Override
    public void show() {
        super.show();
        // TODO pass parameters depending on which level
        generateHighScoresTable(1,5);
    }
    
    private void generateHighScoresTable(int levelid, int limit) {
        highScoreTable.clear();

        highScoreTable.add(new Label("Nick", skin)).pad(10);
        highScoreTable.add(new Label("Time", skin)).pad(10);
        highScoreTable.row();

        List<HighScore> highScores = highScoreInteractor.getBestScoresForLevel(levelid, limit);

        for (HighScore score : highScores) {
            highScoreTable.add(new Label(score.getUsername(), skin)).pad(10);
            highScoreTable.add(new Label(String.valueOf(score.getTime()), skin)).pad(10);
            highScoreTable.row();
        }
    }
}
