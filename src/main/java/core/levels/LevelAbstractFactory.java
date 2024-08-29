package core.levels;

public class LevelAbstractFactory {
    public AbstractLevel createLevel(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> new AbstractLevel("map/map.tmx");
            case 2 -> null;
            default -> null;
        };
    }
}
