package core.orchestrator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class DomainEventEnumTest {
    @Test
    public void testNotNullValue() {
        assertThat(DomainEventEnum.NEEDLEVEL).isNotNull();
    }

    @Test
    public void testEnumName() {
        assertThat(DomainEventEnum.CHANGESCREEN.name()).isEqualTo("CHANGESCREEN");
    }


}
