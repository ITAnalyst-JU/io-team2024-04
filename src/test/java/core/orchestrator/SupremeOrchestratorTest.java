package core.orchestrator;

import core.assets.AssetManagerFactory;
import core.assets.IAssetManager;
import core.levels.ILevelFactory;
import core.levels.ILevelManager;
import core.levels.LevelEnum;
import core.views.IScreenOrchestrator;
import core.views.ScreenEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SupremeOrchestratorTest {
    private IScreenOrchestrator screenOrchestrator;
    private ILevelFactory levelFactory;
    private AssetManagerFactory assetManagerFactory;
    private SupremeOrchestrator orchestrator;

    @BeforeEach
    void setUp() {
        screenOrchestrator = Mockito.mock(IScreenOrchestrator.class);
        levelFactory = Mockito.mock(ILevelFactory.class);
        assetManagerFactory = Mockito.mock(AssetManagerFactory.class);
        orchestrator = new SupremeOrchestrator(screenOrchestrator, levelFactory, assetManagerFactory);
        Mockito.verify(screenOrchestrator).addObserver(orchestrator);
    }

    @Test
    void testConstructor() {
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory);
    }

    @Test
    void testCreate() {
        orchestrator.create();

        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.LOADING, assetManagerFactory);
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory);
    }

    @Test
    void testDispose() {
        var assetManager = Mockito.mock(IAssetManager.class);
        Mockito.when(assetManagerFactory.getAssetManager()).thenReturn(assetManager);

        orchestrator.dispose();

        Mockito.verify(screenOrchestrator).dispose();
        Mockito.verify(assetManagerFactory).getAssetManager();
        Mockito.verify(assetManager).dispose();
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory, assetManager);
    }

    @Test
    void testRespondToEventChangeScreenGeneric() {
        Mockito.when(screenOrchestrator.getNextScreenEnum()).thenReturn(ScreenEnum.PREFERENCES);

        orchestrator.respondToEvent(DomainEventEnum.CHANGESCREEN);

        Mockito.verify(screenOrchestrator).getNextScreenEnum();
        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.PREFERENCES, assetManagerFactory);
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory);
    }

    @Test
    void testRespondToEventChangeScreenEndGame() {
        Mockito.when(screenOrchestrator.getNextScreenEnum()).thenReturn(ScreenEnum.ENDGAME);
        var level = Mockito.mock(ILevelManager.class);
        Mockito.when(levelFactory.getSavedLevel()).thenReturn(level);
        var levelNumber = 17;
        Mockito.when(level.getLevelNumber()).thenReturn(levelNumber);

        orchestrator.respondToEvent(DomainEventEnum.CHANGESCREEN);

        Mockito.verify(screenOrchestrator).getNextScreenEnum();
        Mockito.verify(screenOrchestrator).respondToEndLevel(levelNumber, assetManagerFactory);
        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.ENDGAME, assetManagerFactory);
        Mockito.verify(levelFactory).getSavedLevel();
        Mockito.verify(level).getLevelNumber();
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory, level);
    }

    @Test
    void testRespondToEventChangeScreenMenu() {
        Mockito.when(screenOrchestrator.getNextScreenEnum()).thenReturn(ScreenEnum.MENU);

        orchestrator.respondToEvent(DomainEventEnum.CHANGESCREEN);

        Mockito.verify(screenOrchestrator).getNextScreenEnum();
        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.MENU, assetManagerFactory);
        Mockito.verify(levelFactory).clearSavedLevel();
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory);
    }

    @Test
    void testRespondToEventNeedLevel() {
        var nextLevelEnum = LevelEnum.LEVEL_1;
        Mockito.when(screenOrchestrator.getNextLevel()).thenReturn(nextLevelEnum);
        var nextLevel = Mockito.mock(ILevelManager.class);
        Mockito.when(levelFactory.createLevel(nextLevelEnum, assetManagerFactory)).thenReturn(nextLevel);

        orchestrator.respondToEvent(DomainEventEnum.NEEDLEVEL);

        Mockito.verify(screenOrchestrator).getNextLevel();
        Mockito.verify(levelFactory).createLevel(nextLevelEnum, assetManagerFactory);
        Mockito.verify(screenOrchestrator).respondToLoadedLevel(nextLevel, assetManagerFactory);
        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.GAME, assetManagerFactory);
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory, nextLevel);
    }

    @Test
    void testRespondToEventResumeLevel() {
        var savedLevel = Mockito.mock(ILevelManager.class);
        Mockito.when(levelFactory.getSavedLevel()).thenReturn(savedLevel);

        orchestrator.respondToEvent(DomainEventEnum.RESUME_LEVEL);

        Mockito.verify(levelFactory).getSavedLevel();
        Mockito.verify(screenOrchestrator).respondToLoadedLevel(savedLevel, assetManagerFactory);
        Mockito.verify(screenOrchestrator).getScreen(ScreenEnum.GAME, assetManagerFactory);
        Mockito.verifyNoMoreInteractions(screenOrchestrator, levelFactory, assetManagerFactory, savedLevel);
    }
}