package core.levels;

// NOTE: strategy design pattern
public interface ILevelOrchestrator {
    public AbstractLevel getLevel(LevelEnum levelEnum);
}
