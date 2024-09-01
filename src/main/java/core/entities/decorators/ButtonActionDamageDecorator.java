package core.entities.decorators;

import core.entities.IEntity;

public class ButtonActionDamageDecorator extends BaseEntityDecorator {
    public ButtonActionDamageDecorator(IEntity wrapped, int number) {
        super(wrapped);
    }

    @Override
    public boolean buttonAction(IEntity baseInstance) {
        return baseInstance.damage();
    }
}
