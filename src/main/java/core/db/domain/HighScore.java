package core.db.domain;

public class HighScore {

    private final int scoreId;
    private final int levelId;
    private final String username;
    private final long time;

    public HighScore(int scoreId, int levelId, String username, long time) {
        this.scoreId = scoreId;
        this.levelId = levelId;
        this.username = username;
        this.time = time;
    }

    public int getScoreId() {
        return scoreId;
    }

    public int getLevelId() {
        return levelId;
    }

    public String getUsername() {
        return username;
    }

    public long getTime() {
        return time;
    }
}
