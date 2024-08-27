package core.db.domain;

import core.general.Observable;
import core.general.Observer;

import java.sql.Time;

public class HighScore extends Observable<Observer<HighScore>> {

    private final int scoreId;
    private final int levelId;
    private String username;
    private long time;

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

    public void update(String username, long time) {
        this.username = username;
        this.time = time;
        notifyObservers(observer -> observer.respondToEvent(this));
    }

}
