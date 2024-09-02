package core.entities.decorators;

import core.entities.IEntity;

public class DamageableDecorator extends BaseEntityDecorator {
    private int life;
    private int savedLife;
    public DamageableDecorator(IEntity wrapped, int life) {
        super(wrapped);
        this.life = life;
    }

    @Override
    public boolean damage() {
        life--;
        return (life == 0);
    }

    @Override
    public void saveState() {
        wrapped.saveState();
        savedLife = life;
    }

    @Override
    public void recoverState() {
        wrapped.recoverState();
        life = savedLife;
    }
}
