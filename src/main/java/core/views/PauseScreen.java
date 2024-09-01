package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PauseScreen extends UIScreen {
    private Table highScoreTable;

    public PauseScreen(Stage stage) {
        super(stage);
        setBackgroundImage("ui/background/triangles.png");

        Label titleLabel = createLabel("Game is paused");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        table.add(createButton("Resume", () -> notifyOrchestrator(ScreenEnum.RESUME_GAME))).expandX().padTop(20);
        table.row();

        table.add(createButton("Restart", () -> notifyOrchestrator(ScreenEnum.GAME))).expandX().padTop(20);
        table.row();

        table.add(createButton("Main menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();
    }

    @Override
    public void show() {
        super.show();
    }
}
