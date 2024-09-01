package core.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface IAssetManagerGetter {
    TextureAtlas getTextureAtlas(String assetPath);
    Sound getSound(String assetPath);
    Music getMusic(String assetPath);
    BitmapFont getFont(String assetPath);
    Skin getSkin(String assetPath);

    Map getMap(String assetPath);
}

