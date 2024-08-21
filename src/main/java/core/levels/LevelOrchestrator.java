package core.levels;

import java.util.HashMap;
import java.util.Map;

public class LevelOrchestrator implements ILevelOrchestrator {
    private final LevelAbstractFactory levelAbstractFactory;
    private final Map<Integer, AbstractLevel> levels;
    public LevelOrchestrator(LevelAbstractFactory levelAbstractFactory, Map<Integer, AbstractLevel> levels) {
        this.levelAbstractFactory = levelAbstractFactory;
        this.levels = levels;
    }

    public AbstractLevel getLevel(LevelEnum levelEnum) {
        int levelNumber = levelEnum.getLevelNumber();
        if (!levels.containsKey(levelNumber)) {
            this.loadLevel(levelNumber);
        }
        return levels.get(levelNumber);
    }



    private void loadLevel(int levelNumber) {
        AbstractLevel level = levelAbstractFactory.createLevel(levelNumber);
        levels.put(levelNumber, level);
    }
}
