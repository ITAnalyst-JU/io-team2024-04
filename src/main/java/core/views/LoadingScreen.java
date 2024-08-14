package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.orchestrator.SupremeOrchestrator;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(Stage stage) {
        super(stage);
    }

    @Override
    public void render(float delta) {
        super.notifyOrchestrator(ScreenState.MENU);
    }
}