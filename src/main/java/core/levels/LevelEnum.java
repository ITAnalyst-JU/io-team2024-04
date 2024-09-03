package core.levels;

public enum LevelEnum {
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3);

    private final int levelNumber;

    LevelEnum(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
