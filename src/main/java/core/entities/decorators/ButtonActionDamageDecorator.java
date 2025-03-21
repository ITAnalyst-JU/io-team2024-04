package core.entities.decorators;

import core.entities.IEntity;

public class ButtonActionDamageDecorator extends BaseEntityDecorator {
    public ButtonActionDamageDecorator(IEntity wrapped) {
        super(wrapped);
    }

    @Override
    public boolean buttonAction(IEntity baseInstance) {
        return baseInstance.damage();
    }
}
