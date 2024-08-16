package core.db.domain;

import core.general.Observable;

import java.sql.Time;

public class HighScore extends Observable<HighScore.Observer> {
    public interface Observer {
        void updated(HighScore highScore);
    }

    private final int scoreId;
    private final int levelId;
    private String username;
    private Time time;

    public HighScore(int scoreId, int levelId, String username, Time time) {
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

    public Time getTime() {
        return time;
    }

    public void update(String username, Time time) {
        this.username = username;
        this.time = time;
        notifyObservers(observer -> observer.updated(this));
    }

}
