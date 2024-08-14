package core.levels;

import java.util.HashMap;
import java.util.Map;

public class LevelOrchestrator {
    private final LevelAbstractFactory levelAbstractFactory;
    private final Map<Integer, AbstractLevel> levels;
    public LevelOrchestrator(LevelAbstractFactory levelAbstractFactory) {
        this.levelAbstractFactory = levelAbstractFactory;
        this.levels = new HashMap<>();
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
