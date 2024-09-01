package core.entities.decorators;

import core.entities.IEntity;

public class UserDamageableDecorator extends BaseEntityDecorator {
    public UserDamageableDecorator(IEntity wrapped) {
        super(wrapped);
    }

    @Override
    public boolean getUserDamageable() {
        return true;
    }
}
