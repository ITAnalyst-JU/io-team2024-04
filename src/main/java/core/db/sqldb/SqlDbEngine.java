package core.db.sqldb;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlDbEngine {
    interface Action { void run(PreparedStatement sql) throws SQLException; }

    private final String url;

    SqlDbEngine(String url) { this.url = url; }

    void execute(String statement, Action action) {
        try (var connection = DriverManager.getConnection(url);
             var sql = connection.prepareStatement(statement)) { action.run(sql); }
        catch (SQLException ex) { throw new SqlDbException(ex); }
    }

}
