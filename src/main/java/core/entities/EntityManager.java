package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityManager implements IEntityManager {
    private Set<IEntity> entities;

    private final List<IEntity> entitiesToDispose = new ArrayList<>();

    @Override
    public void update() {
        for (var entity : entities) {
            entity.update();
        }
    }

    @Override
    public void render(Batch batch) {
        for (var entity : entities) {
            entity.draw(batch);
        }
    }

    @Override
    public void loadEntities(List<IEntity> entities) {
        this.entities = new HashSet<>(entities);
    }

    @Override
    public void saveState() {
        for (var entity : entitiesToDispose) {
            entity.dispose();
        }
        entitiesToDispose.clear();
        for (var entity : entities) {
            entity.saveState();
        }
    }

    @Override
    public void recoverState() {
        entities.addAll(entitiesToDispose);
        entitiesToDispose.clear();
        for (var entity : entities) {
            entity.recoverState();
        }
    }

    @Override
    public void remove(IEntity entity) {
        entities.remove(entity);
        entitiesToDispose.add(entity);
        entity.hide();
    }
}
