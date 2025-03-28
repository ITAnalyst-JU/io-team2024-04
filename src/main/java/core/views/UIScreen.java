package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import core.assets.IAssetManagerFactory;
import core.network.HighScoreNetworkInteractor;
import core.db.domain.HighScore;

import java.util.List;
import java.util.function.Consumer;

public abstract class UIScreen extends AbstractScreen {
    protected Skin skin;
    protected BitmapFont font;
    protected Table table;

    public UIScreen(Stage stage, IAssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        initializeUIComponents();
        Gdx.input.setInputProcessor(super.stage);
    }

    private void initializeUIComponents() {
        skin = assetManagerFactory.getAssetManagerGetter().getSkin("ui/skin/plain-james-ui.json");
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
        Texture backgroundTexture = assetManagerFactory.getAssetManagerGetter().getTexture(imagePath);
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.setZIndex(0);
        stage.addActor(backgroundImage);
    }

    protected TextButton createButtonWithHover(String label, Runnable onClickAction, Runnable onHoverEnterAction, Runnable onHoverExitAction) {
        TextButton button = createButton(label, onClickAction);

        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (onHoverEnterAction != null) {
                    onHoverEnterAction.run();
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (onHoverExitAction != null) {
                    onHoverExitAction.run();
                }
            }
        });

        return button;
    }

    private boolean isLoadingHighScores = false;

    protected void generateHighScoresTable(Table highScoreTable, HighScoreNetworkInteractor highScoreInteractor, int levelId, int limit) {
        if (isLoadingHighScores) return; // Prevent multiple loads

        isLoadingHighScores = true;
        highScoreTable.clear();

        highScoreTable.add(createLabel("Nick")).pad(10);
        highScoreTable.add(createLabel("Time")).pad(10);
        highScoreTable.row();

        highScoreInteractor.getBestScores(levelId, limit, new HighScoreNetworkInteractor.Callback<>() {
            @Override
            public void onSuccess(List<HighScore> highScores) {
                Gdx.app.postRunnable(() -> {
                    highScoreTable.clear();
                    if (highScores.isEmpty()) {
                        highScoreTable.add(createLabel("No scores available yet")).expandX().padTop(10);
                    } else {
                        for (HighScore score : highScores) {
                            highScoreTable.add(createLabel(score.getUsername())).pad(10);
                            highScoreTable.add(createLabel(formatTime(score.getTime()))).pad(10);
                            highScoreTable.row();
                        }
                    }
                    isLoadingHighScores = false;
                });
            }

            @Override
            public void onError(String errorMessage) {
                Gdx.app.postRunnable(() -> {
                    highScoreTable.clear();
                    highScoreTable.add(createLabel("Error: " + errorMessage)).expandX().padTop(10);
                    isLoadingHighScores = false;
                });
            }
        });
    }

    protected String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = totalSeconds / 60;
        long millis = milliseconds % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    protected Table createLabelWithBackground(String text) {
        Table table = new Table();
        table.setBackground(skin.getDrawable("round-gray"));
        table.add(createLabel(text));
        return table;
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
    }
}
