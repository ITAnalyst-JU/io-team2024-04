package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import core.orchestrator.SupremeOrchestrator;

import java.util.ArrayList;

public abstract class UIScreen extends AbstractScreen {
    protected Skin skin;
    protected ArrayList<TextButton> buttons;
    protected BitmapFont font;
    protected Table table;

    public UIScreen(Stage stage) {
        super(stage);
        initializeUIComponents();
        Gdx.input.setInputProcessor(super.stage);
        setupGlobalKeyListener();
    }

    private void initializeUIComponents() {
        skin = createSkin();
        font = new BitmapFont();
        buttons = new ArrayList<>();
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    private Skin createSkin() {
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        skin.add("default", textButtonStyle);

        TextButton.TextButtonStyle focusedTextButtonStyle = new TextButton.TextButtonStyle(textButtonStyle);
        focusedTextButtonStyle.fontColor = Color.RED;
        skin.add("focused", focusedTextButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        skin.add("default", labelStyle);

        return skin;
    }

    private void setupGlobalKeyListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return handleKeyDown(event, keycode);
            }
        });
    }

    private boolean handleKeyDown(InputEvent event, int keycode) {
        if (stage.getKeyboardFocus() != null) {
            switch (keycode) {
                case com.badlogic.gdx.Input.Keys.UP:
                case com.badlogic.gdx.Input.Keys.W:
                    moveFocus(FocusDirection.UP);
                    return true;
                case com.badlogic.gdx.Input.Keys.DOWN:
                case com.badlogic.gdx.Input.Keys.S:
                    moveFocus(FocusDirection.DOWN);
                    return true;
                case com.badlogic.gdx.Input.Keys.ENTER:
                case com.badlogic.gdx.Input.Keys.SPACE:
                    if (stage.getKeyboardFocus() instanceof TextButton) {
                        triggerButtonAction((TextButton) stage.getKeyboardFocus());
                    }
                    return true;
            }
        }
        return false;
    }

    protected void addButton(String text, Runnable action) {
        TextButton button = new TextButton(text, skin);
        addClickListener(button, action);
        addFocusListeners(button);
        addToStage(button);
    }

    private void addClickListener(TextButton button, Runnable action) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
    }

    private void addFocusListeners(TextButton button) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setKeyboardFocus(button);
                updateButtonFocus();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (stage.getKeyboardFocus() != button) {
                    button.setStyle(skin.get("default", TextButton.TextButtonStyle.class));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() instanceof TextButton) {
                    TextButton clickedButton = (TextButton) event.getTarget();
                    stage.setKeyboardFocus(clickedButton);
                    updateButtonFocus();
                }
                return false;
            }
        });
    }

    private void addToStage(TextButton button) {
        buttons.add(button);
        table.add(button).expandX().padBottom(10);
        table.row();
        if (buttons.size() > 0) {
            stage.setKeyboardFocus(buttons.getFirst());
        }
    }

    protected void updateButtonFocus() {
        for (TextButton button : buttons) {
            if (stage.getKeyboardFocus() == button) {
                button.setStyle(skin.get("focused", TextButton.TextButtonStyle.class));
            } else {
                button.setStyle(skin.get("default", TextButton.TextButtonStyle.class));
            }
        }
    }

    protected void moveFocus(FocusDirection direction) {
        TextButton currentFocus = (TextButton) stage.getKeyboardFocus();
        int currentIndex = buttons.indexOf(currentFocus);

        if (direction == FocusDirection.UP) {
            currentIndex = (currentIndex > 0) ? currentIndex - 1 : buttons.size() - 1;
        } else if (direction == FocusDirection.DOWN) {
            currentIndex = (currentIndex < buttons.size() - 1) ? currentIndex + 1 : 0;
        }

        stage.setKeyboardFocus(buttons.get(currentIndex));
        updateButtonFocus();
    }

    private void triggerButtonAction(TextButton button) {
        button.toggle();
        InputEvent clickEvent = new InputEvent();
        clickEvent.setType(InputEvent.Type.touchDown);
        button.fire(clickEvent);

        clickEvent.setType(InputEvent.Type.touchUp);
        button.fire(clickEvent);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        updateButtonFocus();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    enum FocusDirection {
        UP, DOWN
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
