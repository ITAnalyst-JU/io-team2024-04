package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.IAssetManagerFactory;
import core.levels.LevelEnum;
import core.network.HighScoreNetworkInteractor;
import core.db.domain.HighScore;
import core.user.UserInteractor;

public class LevelSelectionScreen extends UIScreen {
    private LevelEnum nextLevel;
    private final HighScoreNetworkInteractor highScoreInteractor;
    private final UserInteractor userInteractor;
    private final Label userBestTimeLabel;
    private final Table topScoresTable;
    private final Table labelTable;

    public LevelEnum getNextLevel() {
        return nextLevel;
    }

    public LevelSelectionScreen(Stage stage, IAssetManagerFactory assetManagerFactory, HighScoreNetworkInteractor highScoreInteractor, UserInteractor userInteractor) {
        super(stage, assetManagerFactory);
        this.highScoreInteractor = highScoreInteractor;
        this.userInteractor = userInteractor;

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

        table.add(createButtonWithHover(
                "First adventure",
                () -> {
                    nextLevel = LevelEnum.LEVEL_1;
                    notifyOrchestrator(ScreenEnum.GAME);
                },
                () -> handleHover(LevelEnum.LEVEL_1, "Climbing the Mountain"),
                this::hideHoverInfo
        )).expandX().padBottom(10);
        table.row();

        table.add(createButtonWithHover(
                "Climbing the mountain",
                () -> {
                    nextLevel = LevelEnum.LEVEL_3;
                    notifyOrchestrator(ScreenEnum.GAME);
                },
                () -> handleHover(LevelEnum.LEVEL_3, "Speedrun"),
                this::hideHoverInfo
        )).expandX().padBottom(10);
        table.row();

        table.add(createButtonWithHover(
                "Speedrun",
                () -> {
                    nextLevel = LevelEnum.LEVEL_2;
                    notifyOrchestrator(ScreenEnum.GAME);
                },
                () -> handleHover(LevelEnum.LEVEL_2, "Speedrun"),
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
        highScoreInteractor.getBestScoreForUser(level.getLevelNumber(), userInteractor.getUserName(), new HighScoreNetworkInteractor.Callback<HighScore>() {
            @Override
            public void onSuccess(HighScore userBestScore) {
                Gdx.app.postRunnable(() -> {
                    labelTable.setVisible(true);
                    if (userBestScore != null) {
                        userBestTimeLabel.setText("Your Best Time for " + label + ": " + formatTime(userBestScore.getTime()));
                    } else {
                        userBestTimeLabel.setText("Level not completed");
                    }
                    generateHighScoresTable(topScoresTable, highScoreInteractor, level.getLevelNumber(), 3);
                });
            }

            @Override
            public void onError(String errorMessage) {
                Gdx.app.postRunnable(() -> {
                    labelTable.setVisible(true);
                    userBestTimeLabel.setText("Error: " + errorMessage);
                    generateHighScoresTable(topScoresTable, highScoreInteractor, level.getLevelNumber(), 3);

                });
            }
        });
    }

    private void hideHoverInfo() {
        labelTable.setVisible(false);
    }
}
