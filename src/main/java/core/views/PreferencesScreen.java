package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.orchestrator.SupremeOrchestrator;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(Stage stage) {
        super(stage);
        addButton("Back to Menu", () -> notifyOrchestrator(ScreenState.MENU));
    }
}
