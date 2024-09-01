package core.views;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.assets.AssetManagerFactory;
import core.assets.IAssetManagerLoader;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(Stage stage, AssetManagerFactory assetManagerFactory) {
        super(stage, assetManagerFactory);
    }

    @Override
    public void show() {
        IAssetManagerLoader assetManagerLoader = assetManagerFactory.getAssetManagerLoader();
        assetManagerLoader.loadLoadingScreen();
        assetManagerLoader.loadAtlases();
        assetManagerLoader.loadImages();
        assetManagerLoader.loadSounds();
        assetManagerLoader.loadMusic();
        assetManagerLoader.loadSkins();
        assetManagerLoader.loadFonts();
        assetManagerLoader.loadLevels();

        // just to show background
        try {Thread.sleep(2000); } catch (Exception e) {}
        super.notifyOrchestrator(ScreenEnum.MENU);

    }
}