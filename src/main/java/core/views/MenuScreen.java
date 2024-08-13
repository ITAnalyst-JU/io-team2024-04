package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.orchestrator.SupremeOrchestrator;

public class MenuScreen extends UIScreen {

    // TODO: change to dependency injection
    public MenuScreen(SupremeOrchestrator supremeOrchestrator) {
        super(supremeOrchestrator);

        Label titleLabel = new Label("Gradle Demon Adventures", skin, "default");
        table.top().padTop(50);
        table.add(titleLabel).expandX().padBottom(50);
        table.row();

        addButton("Start game", () -> notifyOrchestator(ScreenState.APPLICATION));
        addButton("Preferences", () -> notifyOrchestator(ScreenState.PREFERENCES));
        addButton("Custom action", () -> System.out.println("Gdzie jest Nero"));
        addButton("Exit", Gdx.app::exit);
    }
}
