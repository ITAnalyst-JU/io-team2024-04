package core.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface IAssetManagerGetter {
    Texture getTexture(String assetPath);

    Pixmap getPixmap(String assetPath);
    TextureAtlas getTextureAtlas(String assetPath);
    Sound getSound(String assetPath);
    Music getMusic(String assetPath);
    BitmapFont getFont(String assetPath);
    Skin getSkin(String assetPath);

    TiledMap getMap(String assetPath);
}

