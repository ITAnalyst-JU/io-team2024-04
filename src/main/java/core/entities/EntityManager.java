package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

public interface EntityManager {
    void loadEntities(List<AbstractEntity> entities);
    void saveState();
    void recoverState();
    void update();
    void render(Batch batch);
}
