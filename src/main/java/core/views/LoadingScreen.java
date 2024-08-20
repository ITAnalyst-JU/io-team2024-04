package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(Stage stage) {
        super(stage);
    }

    @Override
    public void render(float delta) {
        // just to show background
        try {Thread.sleep(1000); } catch (Exception e) {}
        super.notifyOrchestrator(ScreenEnum.MENU);
    }
}