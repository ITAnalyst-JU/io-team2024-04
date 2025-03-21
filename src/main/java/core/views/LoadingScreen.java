package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.assets.IAssetManagerFactory;
import core.assets.IAssetManagerGetter;
import core.assets.IAssetManagerLoader;
import core.parallax.ParallaxBackground;
import core.parallax.ParallaxBackgroundFactory;
import core.parallax.ScreenHook;

public class LoadingScreen extends AbstractScreen {
    private int currentLoadingStage;
    private float delay = 3.f;

    private final ParallaxBackground parallaxBackground;

    public LoadingScreen(Stage stage, IAssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
        IAssetManagerLoader assetManagerLoader = assetManagerFactory.getAssetManagerLoader();
        IAssetManagerGetter assetManagerGetter = assetManagerFactory.getAssetManagerGetter();
        System.out.println("Loading loading screen assets...");
        assetManagerLoader.loadLoadingScreen();
        currentLoadingStage++;
        Pixmap[] parallaxLayersPixmaps = {
                assetManagerGetter.getPixmap("loading_background/sky.png"),
                assetManagerGetter.getPixmap("loading_background/far-clouds.png"),
                assetManagerGetter.getPixmap("loading_background/near-clouds.png"),
                assetManagerGetter.getPixmap("loading_background/far-mountains.png"),
                assetManagerGetter.getPixmap("loading_background/mountains.png"),
                assetManagerGetter.getPixmap("loading_background/trees.png"),
        };
        parallaxBackground = ParallaxBackgroundFactory.createParallaxBackgroundScrolling(parallaxLayersPixmaps, new ScreenHook(0, 0, (int) stage.getWidth(), (int) stage.getHeight()));
        stage.addActor(parallaxBackground);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        IAssetManagerLoader assetManagerLoader = assetManagerFactory.getAssetManagerLoader();
        if (assetManagerLoader.loaded()) {
            currentLoadingStage++;
            switch (currentLoadingStage) {
                case 1:
                    System.out.println("Loading atlases...");
                    assetManagerLoader.queueAtlases();
                    break;
                case 2:
                    System.out.println("Loading images...");
                    assetManagerLoader.queueImages();
                    break;
                case 3:
                    System.out.println("Loading sounds...");
                    assetManagerLoader.queueSounds();
                    break;
                case 4:
                    System.out.println("Loading music...");
                    assetManagerLoader.queueMusic();
                    break;
                case 5:
                    System.out.println("Loading skins...");
                    assetManagerLoader.queueSkins();
                    break;
                case 6:
                    System.out.println("Loading fonts...");
                    assetManagerLoader.queueFonts();
                    break;
                case 7:
                    System.out.println("Loading levels...");
                    assetManagerLoader.queueLevels();
                    break;
                case 8:
                    System.out.println("All assets loaded");
                    break;
            }

            if (currentLoadingStage > 8) {
                currentLoadingStage = 8;
                delay -= delta;
                if (delay <= 0) {
                    notifyOrchestrator(ScreenEnum.MENU);
                }
            }
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        parallaxBackground.resizeHook(width, height);
    }
}