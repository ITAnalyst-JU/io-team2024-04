package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(Stage stage) {
        super(stage);
    }

    @Override
    public void render(float delta) {
        super.notifyOrchestrator(ScreenEnum.MENU);
    }
}