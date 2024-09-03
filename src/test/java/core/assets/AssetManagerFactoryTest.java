package core.assets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.assets.AssetManager;

class AssetManagerFactoryTest {
    @Test
    void testAll() {
        AssetManager assetManager = mock(AssetManager.class);
        AssetManagerFactory assetManagerFactory = new AssetManagerFactory(assetManager);
        IAssetManager outputAssetManager = assetManagerFactory.getAssetManager();
        IAssetManagerGetter outputAssetManagerGetter = assetManagerFactory.getAssetManagerGetter();
        IAssetManagerLoader outputAssetManagerLoader = assetManagerFactory.getAssetManagerLoader();
        assertNotNull(outputAssetManager);
        assertNotNull(outputAssetManagerGetter);
        assertNotNull(outputAssetManagerLoader);
    }
}