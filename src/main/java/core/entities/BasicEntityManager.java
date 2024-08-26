package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

public class BasicEntityManager implements EntityManager {
    private List<AbstractEntity> entities;

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
    public void loadEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }

    @Override
    public void saveState() {
        for (var entity : entities) {
            entity.saveState();
        }
    }

    @Override
    public void recoverState() {
        for (var entity : entities) {
            entity.recoverState();
        }
    }
}
