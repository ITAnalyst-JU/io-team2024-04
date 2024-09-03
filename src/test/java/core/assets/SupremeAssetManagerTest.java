package core.assets;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class SupremeAssetManagerTest {

    @Test
    void testCreate() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);

        assertNotNull(supremeAssetManager);
        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
    }

    @Test
    void testDispose() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.dispose();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).dispose();
        verifyNoMoreInteractions(assetManager);
    }
}