package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class UIScreen extends AbstractScreen {
    protected Skin skin;
    protected ArrayList<TextButton> buttons;
    protected ArrayList<Slider> sliders;
    protected ArrayList<CheckBox> checkboxes;
    protected BitmapFont font;
    protected Table table;

    public UIScreen(Stage stage) {
        super(stage);
        initializeUIComponents();
        Gdx.input.setInputProcessor(super.stage);
    }

    private void initializeUIComponents() {
        skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
        font = new BitmapFont();
        buttons = new ArrayList<>();
        sliders = new ArrayList<>();
        checkboxes = new ArrayList<>();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    protected void addButton(String text, Runnable action) {
        TextButton button = new TextButton(text, skin);
        addClickListener(button, action);
        buttons.add(button);
        table.add(button).expandX().padBottom(10);
        table.row();
    }

    protected void addSlider(String labelText, float min, float max, float stepSize, float initialValue, Runnable action) {
        Label label = new Label(labelText, skin);
        table.add(label).expandX().left().padBottom(10);

        Slider slider = new Slider(min, max, stepSize, false, skin);
        slider.setValue(initialValue);
        slider.addListener(event -> {
            action.run();
            return false;
        });

        sliders.add(slider);
        table.add(slider).expandX().fillX().padBottom(10);
        table.row();
    }

    protected void addCheckbox(String labelText, boolean initialState, Runnable action) {
        CheckBox checkBox = new CheckBox(labelText, skin);
        checkBox.setChecked(initialState);
        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });

        checkboxes.add(checkBox);
        table.add(checkBox).expandX().left().padBottom(10);
        table.row();
    }

    protected void addTextFieldWithButton(String messageText, String buttonText, Consumer<TextField> action) {
        Table inputTable = new Table();
        inputTable.padBottom(10);

        TextField nameField = new TextField("", skin);
        nameField.setMessageText(messageText);

        TextButton updateButton = new TextButton(buttonText, skin);
        updateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.accept(nameField);
                stage.setKeyboardFocus(null);
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        });

        inputTable.add(nameField).width(300).padRight(10);
        inputTable.add(updateButton);

        table.add(inputTable);
        table.row();
    }

    protected float getSliderValue(int index) {
        return sliders.get(index).getValue();
    }

    protected boolean getCheckboxState(int index) {
        return checkboxes.get(index).isChecked();
    }

    private void addClickListener(TextButton button, Runnable action) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
