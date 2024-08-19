package core.db.sqldb;

import core.db.database.DbHighScoreTable;

public class SqlDbFactory {
    public static DbHighScoreTable highScoreTable() {
        SqlDbEngine engine = new SqlDbEngine("jdbc:sqlite:game.db");
        SqlDbHighScoreTable table = new SqlDbHighScoreTable(engine);
        table.createTableIfNotExists();
        return table;
    }
}
