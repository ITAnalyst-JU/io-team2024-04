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
        generateHighScoresTable(1, 5);
    }

    // TODO move it to UIScreen
    private void generateHighScoresTable(int levelid, int limit) {
        highScoreTable.clear();

        highScoreTable.add(createLabel("Nick")).pad(10);
        highScoreTable.add(createLabel("Time")).pad(10);
        highScoreTable.row();

        List<HighScore> highScores = highScoreInteractor.getBestScoresForLevel(levelid, limit);

        for (HighScore score : highScores) {
            highScoreTable.add(createLabel(score.getUsername())).pad(10);
            highScoreTable.add(createLabel(formatTime(score.getTime()))).pad(10);
            highScoreTable.row();
        }
    }

    private String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60;
        long millis = milliseconds % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
}
