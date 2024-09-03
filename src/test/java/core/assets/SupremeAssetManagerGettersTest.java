package core.assets;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class SupremeAssetManagerGettersTest {
    @Test
    void testGetTexture() {
        Texture texture = mock(Texture.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", Texture.class)).thenReturn(texture);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        Texture outputTexture = supremeAssetManager.getTexture("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", Texture.class);
        assertEquals(texture, outputTexture);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetPixmap() {
        Pixmap pixmap = mock(Pixmap.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", Pixmap.class)).thenReturn(pixmap);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        Pixmap outputPixmap = supremeAssetManager.getPixmap("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", Pixmap.class);
        assertEquals(pixmap, outputPixmap);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetTextureAtlas() {
        TextureAtlas textureAtlas = mock(TextureAtlas.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", TextureAtlas.class)).thenReturn(textureAtlas);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        TextureAtlas outputTextureAtlas = supremeAssetManager.getTextureAtlas("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", TextureAtlas.class);
        assertEquals(textureAtlas, outputTextureAtlas);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetSound() {
        Sound sound = mock(Sound.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", Sound.class)).thenReturn(sound);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        Sound outputSound = supremeAssetManager.getSound("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", Sound.class);
        assertEquals(sound, outputSound);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetMusic() {
        Music music = mock(Music.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", Music.class)).thenReturn(music);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        Music outputMusic = supremeAssetManager.getMusic("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", Music.class);
        assertEquals(music, outputMusic);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetFont() {
        BitmapFont bitmapFont = mock(BitmapFont.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", BitmapFont.class)).thenReturn(bitmapFont);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        BitmapFont outputBitmapFont = supremeAssetManager.getFont("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", BitmapFont.class);
        assertEquals(bitmapFont, outputBitmapFont);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetSkin() {
        Skin skin = mock(Skin.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", Skin.class)).thenReturn(skin);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        Skin outputSkin = supremeAssetManager.getSkin("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", Skin.class);
        assertEquals(skin, outputSkin);
        verifyNoMoreInteractions(assetManager);
    }

    @Test
    void testGetMap() {
        TiledMap tiledMap = mock(TiledMap.class);
        AssetManager assetManager = mock(AssetManager.class);
        when(assetManager.get("path", TiledMap.class)).thenReturn(tiledMap);
        SupremeAssetManager supremeAssetManager = new SupremeAssetManager(assetManager);
        TiledMap outputTiledMap = supremeAssetManager.getMap("path");

        verify(assetManager).setLoader(eq(TiledMap.class), any(TmxMapLoader.class));
        verify(assetManager).get("path", TiledMap.class);
        assertEquals(tiledMap, outputTiledMap);
        verifyNoMoreInteractions(assetManager);
    }
}
