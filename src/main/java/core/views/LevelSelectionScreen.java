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

        Label titleLabel = new Label("Gradle Demon Adventures", skin, "default");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        addButton("P = NP", () -> System.out.println("Not for you"));
        addButton("Mario", () -> {
            nextLevel = LevelEnum.LEVEL_1;
            notifyOrchestrator(ScreenEnum.GAME);
        });
        addButton("Back to Menu", () -> notifyOrchestrator(ScreenEnum.MENU));
    }
}
