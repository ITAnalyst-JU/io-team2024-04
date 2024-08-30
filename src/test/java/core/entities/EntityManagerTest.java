package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class EntityManagerTest {

    @Test
    public void testUpdate() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.update();
        Mockito.verify(object1, Mockito.times(1)).update();
        Mockito.verify(object2, Mockito.times(1)).update();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRender() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        var batch = Mockito.mock(Batch.class);
        entityManager.render(batch);
        Mockito.verify(object1, Mockito.times(1)).draw(batch);
        Mockito.verify(object2, Mockito.times(1)).draw(batch);
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testUpdateRenderAdvanced() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.update();
        entityManager.render(null);
        entityManager.update();
        entityManager.render(null);
        Mockito.verify(object1, Mockito.times(2)).update();
        Mockito.verify(object1, Mockito.times(2)).draw(null);
        Mockito.verify(object2, Mockito.times(2)).update();
        Mockito.verify(object2, Mockito.times(2)).draw(null);
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testSaveState() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.saveState();
        Mockito.verify(object1, Mockito.times(1)).saveState();
        Mockito.verify(object2, Mockito.times(1)).saveState();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRecoverState() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.recoverState();
        Mockito.verify(object1, Mockito.times(1)).recoverState();
        Mockito.verify(object2, Mockito.times(1)).recoverState();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRemove() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.remove(object1);
        Mockito.verify(object1, Mockito.times(1)).hide();
        Mockito.verifyNoMoreInteractions(object1);
        Mockito.verifyNoInteractions(object2);
    }

    @Test
    public void testRemoveAdvanced() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.remove(object1);
        entityManager.update();
        entityManager.render(null);
        Mockito.verify(object1, Mockito.times(1)).hide();
        Mockito.verify(object2, Mockito.times(1)).update();
        Mockito.verify(object2, Mockito.times(1)).draw(null);
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRemoveSaveDeletes() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.remove(object1);
        entityManager.saveState();
        Mockito.verify(object1, Mockito.times(1)).hide();
        Mockito.verify(object1, Mockito.times(1)).dispose();
        Mockito.verify(object2, Mockito.times(1)).saveState();
        Mockito.verifyNoMoreInteractions(object1, object2);
    }

    @Test
    public void testRemoveRecover() {
        var object1 = Mockito.mock(BodyOnlyEntity.class);
        var object2 = Mockito.mock(BodyOnlyEntity.class);
        List<BodyOnlyEntity> entities = List.of(object1, object2);
        EntityManager entityManager = new BasicEntityManager();
        entityManager.loadEntities(entities);
        entityManager.saveState();
        entityManager.remove(object1);
        entityManager.recoverState();
        entityManager.update();
        entityManager.render(null);
        Mockito.verify(object1, Mockito.times(1)).saveState();
        Mockito.verify(object1, Mockito.times(1)).hide();
        Mockito.verify(object1, Mockito.times(1)).recoverState();
        Mockito.verify(object1, Mockito.times(1)).update();
        Mockito.verify(object1, Mockito.times(1)).draw(null);
        Mockito.verify(object2, Mockito.times(1)).saveState();
        Mockito.verify(object2, Mockito.times(1)).recoverState();
        Mockito.verify(object2, Mockito.times(1)).update();
        Mockito.verify(object2, Mockito.times(1)).draw(null);
        Mockito.verifyNoMoreInteractions(object1);
        Mockito.verifyNoMoreInteractions(object2);
    }
}
