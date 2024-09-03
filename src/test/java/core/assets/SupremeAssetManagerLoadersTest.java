package core.assets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SupremeAssetManagerLoadersTest {
    // NOTE: 93% line coverage, because some paths are empty ;)
    @Test
    void testLoadLoadingScreen() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.loadLoadingScreen();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_LOADING_SCREEN) {
            verify(assetManager).load(path, Pixmap.class);
        }
        verify(assetManager).finishLoading();
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueImages() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueImages();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_IMAGES) {
            verify(assetManager).load(path, Texture.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueAtlases() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueAtlases();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_ATLASES) {
            verify(assetManager).load(path, TextureAtlas.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueSounds() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueSounds();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_SOUNDS) {
            verify(assetManager).load(path, Sound.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueMusic() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueMusic();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_MUSIC) {
            verify(assetManager).load(path, Music.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueFonts() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueFonts();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_FONTS) {
            verify(assetManager).load(path, BitmapFont.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueSkins() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueSkins();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_SKINS) {
            verify(assetManager).load(path, Skin.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testQueueLevels() {
        AssetManager assetManager = mock(AssetManager.class);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        supremeAssetManager.queueLevels();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        for (String path : Paths.PATHS_LEVELS) {
            verify(assetManager).load(path, TiledMap.class);
        }
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testLoadedTrue() {
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.update()).thenReturn(true);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        boolean loaded = supremeAssetManager.loaded();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).update();
        assertTrue(loaded);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testLoadedFalse() {
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.update()).thenReturn(false);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        boolean loaded = supremeAssetManager.loaded();

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).update();
        assertFalse(loaded);
        verifyNoMoreInteractions(assetManager);
    }
}
