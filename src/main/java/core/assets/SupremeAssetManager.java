package core.assets;

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

public class SupremeAssetManager implements IAssetManager {

    private final AssetManager assetManager;

    public SupremeAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public Texture getTexture(String assetPath) {
        return assetManager.get(assetPath, Texture.class);
    }

    @Override
    public Pixmap getPixmap(String assetPath) {return assetManager.get(assetPath, Pixmap.class); }
    @Override
    public TextureAtlas getTextureAtlas(String assetPath) {
        return assetManager.get(assetPath, TextureAtlas.class);
    }

    @Override
    public Sound getSound(String assetPath) {
        return assetManager.get(assetPath, Sound.class);
    }

    @Override
    public Music getMusic(String assetPath) {
        return assetManager.get(assetPath, Music.class);
    }

    @Override
    public BitmapFont getFont(String assetPath) {
        return assetManager.get(assetPath, BitmapFont.class);
    }

    @Override
    public Skin getSkin(String assetPath) {
        return assetManager.get(assetPath, Skin.class);
    }

    @Override
    public TiledMap getMap(String assetPath) {
        return assetManager.get(assetPath, TiledMap.class);
    }

    @Override
    public void loadLoadingScreen() {
        for (String path : Paths.PATHS_LOADING_SCREEN) {
            assetManager.load(path, Pixmap.class);
        }
        assetManager.finishLoading();
    }

    @Override
    public void queueImages() {
        for (String path : Paths.PATHS_IMAGES) {
            assetManager.load(path, Texture.class);
        }
    }

    @Override
    public void queueAtlases() {
        for (String path : Paths.PATHS_ATLASES) {
            assetManager.load(path, TextureAtlas.class);
        }
    }

    @Override
    public void queueSounds() {
        for (String path : Paths.PATHS_SOUNDS) {
            assetManager.load(path, Sound.class);
        }
    }

    @Override
    public void queueMusic() {
        for (String path : Paths.PATHS_MUSIC) {
            assetManager.load(path, Music.class);
        }
    }

    @Override
    public void queueFonts() {
        for (String path : Paths.PATHS_FONTS) {
            assetManager.load(path, BitmapFont.class);
        }
    }

    @Override
    public void queueSkins() {
        for (String path : Paths.PATHS_SKINS) {
            assetManager.load(path, Skin.class);
        }
    }

    @Override
    public void queueLevels() {
        for (String path : Paths.PATHS_LEVELS) {
            assetManager.load(path, TiledMap.class);
        }
    }

    @Override
    public boolean loaded() {
        return assetManager.update();
    }
}
