package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.levels.LevelEnum;
import core.db.app.HighScoreInteractor;
import core.db.domain.HighScore;

public class LevelSelectionScreen extends UIScreen {
    private LevelEnum nextLevel;
    private final HighScoreInteractor highScoreInteractor;
    private final Label userBestTimeLabel;
    private final Table topScoresTable;
    private final Table labelTable;

    public LevelEnum getNextLevel() {
        return nextLevel;
    }

    public LevelSelectionScreen(Stage stage, HighScoreInteractor highScoreInteractor) {
        super(stage);
        this.highScoreInteractor = highScoreInteractor;

        setBackgroundImage("ui/background/triangles.png");

        Table greyBackground = new Table();
        greyBackground.setBackground(skin.getDrawable("round-gray"));
        Label titleLabel = createLabel("Select Level");
        greyBackground.add(titleLabel);
        table.add(greyBackground).expandX().padBottom(20).center();
        table.row();

        table.top().left();
        table.add(greyBackground).expandX().padTop(20).padBottom(10);
        table.row();

        table.add(createButton("P = NP", () -> System.out.println("Not for you"))).expandX().padBottom(10);
        table.row();

        table.add(createButtonWithHover(
                "Mario",
                () -> {
                    nextLevel = LevelEnum.LEVEL_1;
                    notifyOrchestrator(ScreenEnum.GAME);
                },
                () -> handleHover(LevelEnum.LEVEL_1, "Mario"),
                this::hideHoverInfo
        )).expandX().padBottom(10);
        table.row();

        table.add(createButtonWithHover(
                "Mario 2",
                () -> {
                    nextLevel = LevelEnum.LEVEL_2;
                    notifyOrchestrator(ScreenEnum.GAME);
                },
                () -> handleHover(LevelEnum.LEVEL_2, "Mario 2"),
                this::hideHoverInfo
        )).expandX().padBottom(10);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();

        labelTable = new Table();
        labelTable.setBackground(skin.getDrawable("round-gray"));
        labelTable.setVisible(false);
        userBestTimeLabel = createLabel("");
        topScoresTable = new Table();
        labelTable.add(userBestTimeLabel).expandX().padTop(10).row();
        labelTable.add(topScoresTable).expandX().padTop(10).row();

        table.add(labelTable).padTop(20);
    }

    private void handleHover(LevelEnum level, String label) {
        HighScore userBestScore = highScoreInteractor.getBestScoreForUserAndLevel(level.getLevelNumber(), "sigma"); // TODO get username from preferences

        labelTable.setVisible(true);
        if (userBestScore != null) {
            userBestTimeLabel.setText("Your Best Time for " + label + ": " + formatTime(userBestScore.getTime()));
        } else {
            userBestTimeLabel.setText("Level not completed");
        }

        generateHighScoresTable(topScoresTable, highScoreInteractor, level.getLevelNumber(), 3);
    }

    private void hideHoverInfo() {
        labelTable.setVisible(false);
    }
}
