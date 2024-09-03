package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import core.assets.AssetManagerFactory;

public class Tutorial2Screen extends UIScreen {

    public Tutorial2Screen(Stage stage, AssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        setBackgroundImage("ui/background/tutorial2.png");

        table.add(createLabelWithBackground("Tutorial")).padTop(50);
        table.row();

        Table buttonTable = new Table();
        buttonTable.top().right();
        buttonTable.padTop(50).padRight(50);

        var nextButton = createButton("Previous", () -> notifyOrchestrator(ScreenEnum.TUTORIAL));
        var mainMenuButton = createButton("Main menu", () -> notifyOrchestrator(ScreenEnum.MENU));

        buttonTable.add(nextButton).padRight(10);
        buttonTable.add(mainMenuButton);
        table.add(buttonTable).expand().fill();
    }
}
