package core.levels;

public enum LevelEnum {
    LEVEL_1(1),
    LEVEL_2(2);

    private final int levelNumber;

    LevelEnum(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
