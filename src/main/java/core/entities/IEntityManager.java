package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

public interface IEntityManager {
    void loadEntities(List<IEntity> entities);
    void saveState();
    void recoverState();
    void update();
    void render(Batch batch);
    void remove(IEntity entity);
}
