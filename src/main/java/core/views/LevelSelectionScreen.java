package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.levels.LevelEnum;

public class LevelSelectionScreen extends UIScreen {
    private LevelEnum nextLevel;

    public LevelEnum getNextLevel() {
        return nextLevel;
    }

    public LevelSelectionScreen(Stage stage) {
        super(stage);

        Label titleLabel = createLabel("Gradle Demon Adventures");

        table.center();
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        table.add(createButton("P = NP", () -> System.out.println("Not for you"))).expandX().padBottom(10);
        table.row();

        table.add(createButton("Mario", () -> {
            nextLevel = LevelEnum.LEVEL_1;
            notifyOrchestrator(ScreenEnum.GAME);
        })).expandX().padBottom(10);
        table.row();

        table.add(createButton("Mario 2", () -> {
            nextLevel = LevelEnum.LEVEL_2;
            notifyOrchestrator(ScreenEnum.GAME);
        })).expandX().padBottom(10);
        table.row();

        table.add(createButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();
    }
}
