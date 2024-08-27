package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicEntityManager implements EntityManager {
    private Set<BodyEntity> entities;

    private final List<BodyEntity> entitiesToDispose = new ArrayList<>();

    private LevelContactListener contactListener;

    public BasicEntityManager(LevelContactListener contactListener) {
        this.contactListener = contactListener;
    }

    @Override
    public void update() {
        for (var entity : contactListener.getBodiesToRemove()) {
            entities.remove(entity);
            entitiesToDispose.add(entity);
            entity.hide();
        }
        contactListener.clearBodiesToRemove();
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
    public void loadEntities(List<BodyEntity> entities) {
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
}
