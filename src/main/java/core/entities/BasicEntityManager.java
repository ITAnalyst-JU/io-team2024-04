package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicEntityManager implements EntityManager {
    private Set<BodyOnlyEntity> entities;

    private final List<BodyOnlyEntity> entitiesToDispose = new ArrayList<>();

    public BasicEntityManager() {

    }

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
    public void loadEntities(List<BodyOnlyEntity> entities) {
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
    public void remove(BodyOnlyEntity entity) {
        entities.remove(entity);
        entitiesToDispose.add(entity);
        entity.hide();
    }
}
