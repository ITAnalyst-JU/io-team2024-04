package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.orchestrator.SupremeOrchestrator;

public class MenuScreen extends UIScreen {
    public MenuScreen(Stage stage) {
        super(stage);

        Label titleLabel = new Label("Gradle Demon Adventures", skin, "default");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        addButton("Start game", () -> notifyOrchestrator(ScreenState.APPLICATION));
        addButton("Preferences", () -> notifyOrchestrator(ScreenState.PREFERENCES));
        addButton("Custom action", () -> System.out.println("Gdzie jest Nero"));
        addButton("Exit", Gdx.app::exit);
    }
}
