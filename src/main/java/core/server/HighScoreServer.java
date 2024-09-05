package core.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.db.app.HighScoreGateway;
import core.db.app.HighScoreInteractor;
import core.db.app.HighScoreInteractorWithGateway;
import core.db.database.DbHighScoreGateway;
import core.db.sqldb.SqlDbFactory;

import static spark.Spark.*;

public class HighScoreServer {
    public static void main(String[] args) {
        HighScoreGateway highScoreGateway = new DbHighScoreGateway(SqlDbFactory.highScoreTable());
        HighScoreInteractor highScoreInteractor = new HighScoreInteractorWithGateway(highScoreGateway);
        HighScoreController controller = new HighScoreController(highScoreInteractor);

        port(8080);

        post("/highscores", (req, res) -> {
            JsonObject requestBody = new Gson().fromJson(req.body(), JsonObject.class);
            int levelId = requestBody.get("levelId").getAsInt();
            String username = requestBody.get("username").getAsString();
            long time = requestBody.get("time").getAsLong();
            return controller.addHighScore(levelId, username, time);
        });

        get("/highscores/top/:levelId", (req, res) -> {
            int levelId = Integer.parseInt(req.params(":levelId"));
            int limit = req.queryParams("limit") != null ? Integer.parseInt(req.queryParams("limit")) : 10;
            return controller.getBestScores(levelId, limit);
        });

        get("/highscores/best/:levelId/:username", (req, res) -> {
            int levelId = Integer.parseInt(req.params(":levelId"));
            String username = req.params(":username");
            return controller.getBestScoreForUser(levelId, username);
        });
    }
}