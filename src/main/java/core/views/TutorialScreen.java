package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.IAssetManagerFactory;

public class TutorialScreen extends UIScreen {

    public TutorialScreen(Stage stage, IAssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        setBackgroundImage("ui/background/tutorial.png");

        table.add(createLabelWithBackground("Tutorial")).padTop(50);
        table.row();

        Table buttonTable = new Table();
        buttonTable.top().right();
        buttonTable.padTop(50).padRight(50);

        var nextButton = createButton("Next", () -> notifyOrchestrator(ScreenEnum.TUTORIAL2));
        var mainMenuButton = createButton("Main menu", () -> notifyOrchestrator(ScreenEnum.MENU));

        buttonTable.add(nextButton).padRight(10);
        buttonTable.add(mainMenuButton);
        table.add(buttonTable).expand().fill();
    }
}
