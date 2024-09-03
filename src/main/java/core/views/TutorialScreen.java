package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.AssetManagerFactory;

public class TutorialScreen extends UIScreen {
    private Table highScoreTable;

    public TutorialScreen(Stage stage, AssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        setBackgroundImage("ui/background/tutorial.png");

        Label titleLabel = createLabel("Tutorial");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();
        table.add(createButton("Next", () -> notifyOrchestrator(ScreenEnum.TUTORIAL2))).expandX().padTop(20);
        table.row();
        table.add(createButton("Main menu", () -> notifyOrchestrator(ScreenEnum.MENU))).expandX().padTop(20);
        table.row();
    }

    @Override
    public void show() {
        super.show();
    }
}
