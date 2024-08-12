package core.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.orchestrator.SupremeOrchestrator;

public class PreferencesScreen extends AbstractScreen {

    private Label label;
    private Label.LabelStyle labelStyle;
    public PreferencesScreen(SupremeOrchestrator supremeOrchestrator) {
        super(supremeOrchestrator);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        label = new Label("Hello, Stage!", labelStyle);
        label.setPosition(100, 150);

        super.stage.addActor(label);
    }

}
