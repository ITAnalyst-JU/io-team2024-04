package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Program;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {
    private final Program game;
    private Stage stage;
    private Skin skin;
    private List<TextButton> buttons;

    public MainMenuScreen(final Program game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
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

        TextButton startButton = new TextButton("Start game", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        buttons = new ArrayList<>();
        buttons.add(startButton);
        buttons.add(optionsButton);
        buttons.add(exitButton);

        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(50);

        table.add(new Label("The Prophet", skin, "default")).expandX().padBottom(50);
        table.row();
        for (TextButton button : buttons) {
            table.add(button).expandX().padBottom(10);
            table.row();
        }

        stage.addActor(table);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        for (TextButton button : buttons) {
            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    stage.setKeyboardFocus(button);
                    updateButtonFocus();
                }
            });
        }

        stage.setKeyboardFocus(startButton);
        updateButtonFocus();

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (stage.getKeyboardFocus() != null) {
                    switch (keycode) {
                        case com.badlogic.gdx.Input.Keys.UP:
                            moveFocus(Direction.UP);
                            return true;
                        case com.badlogic.gdx.Input.Keys.DOWN:
                            moveFocus(Direction.DOWN);
                            return true;
                        case com.badlogic.gdx.Input.Keys.ENTER:
                        case com.badlogic.gdx.Input.Keys.SPACE:
                            if (stage.getKeyboardFocus() instanceof TextButton focusedButton) {
                                focusedButton.toggle();
                                InputEvent clickEvent = new InputEvent();
                                clickEvent.setType(InputEvent.Type.touchDown);
                                focusedButton.fire(clickEvent);

                                clickEvent.setType(InputEvent.Type.touchUp);
                                focusedButton.fire(clickEvent);
                            }
                            return true;
                    }
                }
                return false;
            }
        });
    }

    private void moveFocus(Direction direction) {
        TextButton currentFocus = (TextButton) stage.getKeyboardFocus();
        int currentIndex = buttons.indexOf(currentFocus);

        if (direction == Direction.UP) {
            if (currentIndex > 0) {
                stage.setKeyboardFocus(buttons.get(currentIndex - 1));
            } else {
                stage.setKeyboardFocus(buttons.getLast());
            }
        } else if (direction == Direction.DOWN) {
            if (currentIndex < buttons.size() - 1) {
                stage.setKeyboardFocus(buttons.get(currentIndex + 1));
            } else {
                stage.setKeyboardFocus(buttons.getFirst());
            }
        }
        updateButtonFocus();
    }

    private void updateButtonFocus() {
        for (TextButton button : buttons) {
            if (stage.getKeyboardFocus() == button) {
                button.setStyle(skin.get("focused", TextButton.TextButtonStyle.class));
            } else {
                button.setStyle(skin.get("default", TextButton.TextButtonStyle.class));
            }
        }
    }

    enum Direction {
        UP,
        DOWN
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
