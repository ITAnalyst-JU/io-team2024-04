package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.function.Consumer;

public abstract class UIScreen extends AbstractScreen {
    protected Skin skin;
    protected BitmapFont font;
    protected Table table;

    public UIScreen(Stage stage) {
        super(stage);
        initializeUIComponents();
        Gdx.input.setInputProcessor(super.stage);
    }

    private void initializeUIComponents() {
        skin = new Skin(Gdx.files.internal("ui/skin/plain-james-ui.json"));
        font = new BitmapFont();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    protected TextButton createButton(String text, Runnable action) {
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
        return button;
    }

    protected Slider createSlider(float min, float max, float stepSize, float initialValue, Consumer<Float> onChange) {
        Slider slider = new Slider(min, max, stepSize, false, skin);
        slider.setValue(initialValue);
        slider.addListener(event -> {
            onChange.accept(slider.getValue());
            return false;
        });
        return slider;
    }

    protected CheckBox createCheckbox(String labelText, boolean initialState, Consumer<Boolean> onChange) {
        CheckBox checkBox = new CheckBox(labelText, skin);
        checkBox.setChecked(initialState);
        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onChange.accept(checkBox.isChecked());
            }
        });
        return checkBox;
    }

    protected Label createLabel(String text) {
        return new Label(text, skin);
    }

    protected TextField createTextField(String messageText) {
        TextField textField = new TextField("", skin);
        textField.setMessageText(messageText);
        return textField;
    }

    protected void setBackgroundImage(String imagePath) {
        Texture backgroundTexture = new Texture(Gdx.files.internal(imagePath));
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.setZIndex(0);
        stage.addActor(backgroundImage);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        table.setZIndex(1);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                actor.setSize(width, height);
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
