package core.db.sqldb;

import java.sql.SQLException;

public class SqlDbException extends RuntimeException {
    SqlDbException(SQLException cause) { super(cause); }
}
