package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.orchestrator.SupremeOrchestrator;

public class PreferencesScreen extends UIScreen {

    public PreferencesScreen(SupremeOrchestrator supremeOrchestrator) {
        super(supremeOrchestrator);
        addButton("Back to Menu", () -> notifyOrchestator(ScreenState.MENU));
    }
}
