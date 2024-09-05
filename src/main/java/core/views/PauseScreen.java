package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.IAssetManagerFactory;

public class PauseScreen extends UIScreen {
    private Table highScoreTable;

    public PauseScreen(Stage stage, IAssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        setBackgroundImage("ui/background/preview.png");

        Table titleLabel = createLabelWithBackground("Game is paused");
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
