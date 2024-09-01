package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class BasicEntityManagerTest {
    EntityManager manager;
    IEntity object1;
    IEntity object2;

    @BeforeEach
    public void setUp() {
        manager = new BasicEntityManager();
        object1 = Mockito.mock(IEntity.class);
        object2 = Mockito.mock(IEntity.class);
        manager.loadEntities(List.of(object1, object2));
    }

    @Test
    public void testUpdate() {
        manager.update();

        Mockito.verify(object1).update();
        Mockito.verify(object2).update();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRender() {
        Batch batch = Mockito.mock(Batch.class);

        manager.render(batch);
        Mockito.verify(object1).draw(
                batch);
        Mockito.verify(object2).draw(batch);
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testSaveState() {
        manager.saveState();

        Mockito.verify(object1).saveState();
        Mockito.verify(object2).saveState();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRecoverState() {
        manager.recoverState();

        Mockito.verify(object1).recoverState();
        Mockito.verify(object2).recoverState();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRemove() {
        manager.remove(object1);
        manager.update();

        Mockito.verify(object1).hide();
        Mockito.verify(object2).update();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRemoveAndDispose() {
        manager.remove(object1);
        manager.saveState();
        manager.update();

        Mockito.verify(object1).hide();
        Mockito.verify(object1).dispose();
        Mockito.verify(object2).saveState();
        Mockito.verify(object2).update();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }
}
