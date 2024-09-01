package core.entities.decorators;

import core.entities.IEntity;

public class DamageableDecorator extends BaseEntityDecorator {
    private int life;
    public DamageableDecorator(IEntity wrapped, int life) {
        super(wrapped);
        this.life = life;
    }

    @Override
    public boolean damage() {
        life--;
        return (life == 0);
    }
}
