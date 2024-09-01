package core.entities.decorators;

import core.entities.IEntity;

public class ButtonDecorator extends BaseEntityDecorator {
    private final int number;
    public ButtonDecorator(IEntity wrapped, int number) {
        super(wrapped);
        this.number = number;
    }

    @Override
    public int getButtonNumber() {
        return number;
    }
}
