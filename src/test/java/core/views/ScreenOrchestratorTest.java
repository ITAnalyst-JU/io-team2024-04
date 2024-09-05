package core.views;

import core.assets.AssetManagerFactory;
import core.levels.LevelEnum;
import core.levels.LevelManager;
import core.orchestrator.SupremeOrchestrator;
import core.parallax.ScreenHook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static core.orchestrator.DomainEventEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class ScreenOrchestratorTest {
    private ScreenAbstractFactory screenAbstractFactory;
    private Map<ScreenEnum, AbstractScreen> screens;

    private AssetManagerFactory assetManagerFactory;

    @BeforeEach
    public void setUp() {
        screenAbstractFactory = mock(ScreenAbstractFactory.class);
        screens = mock(Map.class);
        assetManagerFactory = mock(AssetManagerFactory.class);
    }

    @Test
    public void testRespondToScreenEnumGame() {
        var supremeOrchestrator = mock(SupremeOrchestrator.class);
        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.addObserver(supremeOrchestrator);
        screenOrchestrator.respondToEvent(ScreenEnum.GAME);

        assertThat(screenOrchestrator.getNextScreenEnum()).isEqualTo(ScreenEnum.GAME);
        verify(supremeOrchestrator, times(1)).respondToEvent(NEEDLEVEL);
    }

    @Test
    public void testRespondToScreenEnumResumeGame() {
        var supremeOrchestrator = mock(SupremeOrchestrator.class);
        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.addObserver(supremeOrchestrator);
        screenOrchestrator.respondToEvent(ScreenEnum.RESUME_GAME);

        assertThat(screenOrchestrator.getNextScreenEnum()).isEqualTo(ScreenEnum.RESUME_GAME);
        verify(supremeOrchestrator, times(1)).respondToEvent(RESUME_LEVEL);
    }

    @Test
    public void testRespondToScreenEnum() {
        var supremeOrchestrator = mock(SupremeOrchestrator.class);
        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.addObserver(supremeOrchestrator);
        screenOrchestrator.respondToEvent(ScreenEnum.MENU);

        assertThat(screenOrchestrator.getNextScreenEnum()).isEqualTo(ScreenEnum.MENU);
        verify(supremeOrchestrator, times(1)).respondToEvent(CHANGESCREEN);
    }

    @Test
    public void testDispose() {
        var screen = mock(LoadingScreen.class);
        screens = new HashMap<>();
        screens.put(ScreenEnum.LOADING, screen);
        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.dispose();
        assertThat(screens).isEmpty();
        verify(screen, times(1)).dispose();
        verify(screen, times(1)).removeObserver(screenOrchestrator);
    }

    @Test
    public void testGetNextLevel() {
        var levelSelectionScreen = mock(LevelSelectionScreen.class);
        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        when(levelSelectionScreen.getNextLevel()).thenReturn(LevelEnum.LEVEL_3);
        when(screens.get(ScreenEnum.LEVELSELECTION)).thenReturn(levelSelectionScreen);
        assertThat(screenOrchestrator.getNextLevel()).isEqualTo(LevelEnum.LEVEL_3);
    }

    @Test
    public void testRespondToEndLevelExistsInMap() {
        var screen = mock(EndScreen.class);
        when(screens.containsKey(ScreenEnum.ENDGAME)).thenReturn(true);
        when(screens.get(ScreenEnum.ENDGAME)).thenReturn(screen);
        when(screenAbstractFactory.createEndScreen(3, assetManagerFactory)).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.respondToEndLevel(3, assetManagerFactory);

        verify(screen, times(1)).dispose();
        verify(screens, times(1)).remove(ScreenEnum.ENDGAME);
        verify(screenAbstractFactory, times(1)).createEndScreen(3, assetManagerFactory);
        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.ENDGAME, screen);
    }

    @Test
    public void testRespondToEndLevelDoesNotExistInMap() {
        var screen = mock(EndScreen.class);
        when(screens.containsKey(ScreenEnum.ENDGAME)).thenReturn(false);
        when(screenAbstractFactory.createEndScreen(3, assetManagerFactory)).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.respondToEndLevel(3, assetManagerFactory);

        verify(screen, never()).dispose();
        verify(screens, never()).remove(ScreenEnum.ENDGAME);

        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.ENDGAME, screen);
    }

    @Test
    public void testRespondToLoadedLevelExistsInMap() {
        var screen = mock(MainScreen.class);
        when(screens.containsKey(ScreenEnum.GAME)).thenReturn(true);
        when(screens.get(ScreenEnum.GAME)).thenReturn(screen);
        when(screenAbstractFactory.createMainScreen(any(), any())).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.respondToLoadedLevel(mock(LevelManager.class), assetManagerFactory);

        verify(screen, times(1)).dispose();
        verify(screens, times(1)).remove(ScreenEnum.GAME);

        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.GAME, screen);
    }

    @Test
    public void testRespondToLoadedLevelDoesNotExistInMap() {
        var screen = mock(MainScreen.class);
        when(screens.containsKey(ScreenEnum.GAME)).thenReturn(false);
        when(screenAbstractFactory.createMainScreen(any(), any())).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.respondToLoadedLevel(mock(LevelManager.class), assetManagerFactory);

        verify(screen, never()).dispose();
        verify(screens, never()).remove(ScreenEnum.GAME);

        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.GAME, screen);
    }

    @Test
    public void testGetScreenExistsInMap() {
        var screen = mock(TutorialScreen.class);
        when(screens.containsKey(ScreenEnum.TUTORIAL)).thenReturn(true);
        when(screens.get(ScreenEnum.TUTORIAL)).thenReturn(screen);
        when(screenAbstractFactory.createScreen(ScreenEnum.TUTORIAL, assetManagerFactory)).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.getScreen(ScreenEnum.TUTORIAL, assetManagerFactory);

        verify(screen, times(1)).dispose();
        verify(screens, times(1)).remove(ScreenEnum.TUTORIAL);

        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.TUTORIAL, screen);
    }

    @Test
    public void testGetScreenDoesNotExistInMap() {
        var screen = mock(TutorialScreen.class);
        when(screens.containsKey(ScreenEnum.TUTORIAL)).thenReturn(false);
        when(screenAbstractFactory.createScreen(ScreenEnum.TUTORIAL, assetManagerFactory)).thenReturn(screen);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.getScreen(ScreenEnum.TUTORIAL, assetManagerFactory);

        verify(screen, never()).dispose();
        verify(screens, never()).remove(ScreenEnum.TUTORIAL);

        verify(screen, times(1)).addObserver(screenOrchestrator);
        verify(screens, times(1)).put(ScreenEnum.TUTORIAL, screen);
    }

    @Test
    public void testGetScreenGameOrEndGame() {
        var screenGame = mock(MainScreen.class);
        var screenEnd = mock(EndScreen.class);
        when(screens.get(ScreenEnum.GAME)).thenReturn(screenGame);
        when(screens.get(ScreenEnum.ENDGAME)).thenReturn(screenEnd);

        var screenOrchestrator = new ScreenOrchestrator(screenAbstractFactory, screens);
        screenOrchestrator.getScreen(ScreenEnum.GAME, assetManagerFactory);

        verify(screens, times(1)).get(ScreenEnum.GAME);

        screenOrchestrator.getScreen(ScreenEnum.ENDGAME, assetManagerFactory);
        verify(screens, times(1)).get(ScreenEnum.ENDGAME);

        verifyNoMoreInteractions(screens);
    }




}
