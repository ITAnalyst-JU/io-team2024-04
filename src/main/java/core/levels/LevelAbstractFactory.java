package core.levels;

import com.badlogic.gdx.math.Vector2;
import core.utilities.Constants;

public class LevelAbstractFactory {
    public AbstractLevel createLevel(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> new AbstractLevel(new Vector2(110, 500), "map/map2.tmx");
            case 2 -> null;
            default -> null;
        };
    }
}
