package core.db.sqldb;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SqlDbExceptionTest {
    @Test
    void testSqlDbException() {
        SqlDbException sqlDbException = new SqlDbException(null);
        assertThat(sqlDbException).isNotNull();
        assertThat(sqlDbException).isInstanceOf(RuntimeException.class);
    }
}
