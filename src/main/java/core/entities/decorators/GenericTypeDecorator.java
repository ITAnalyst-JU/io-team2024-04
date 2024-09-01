package core.entities.decorators;

import core.entities.IEntity;

public class GenericTypeDecorator extends BaseEntityDecorator {
    private final String type;
    public GenericTypeDecorator(IEntity wrapped, String type) {
        super(wrapped);
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
