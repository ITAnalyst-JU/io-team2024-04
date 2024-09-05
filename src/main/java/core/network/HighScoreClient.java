package core.network;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import core.db.domain.HighScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HighScoreClient {
    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    public HighScoreClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void addHighScore(int levelId, String username, long time, Callback<Void> callback) {
        HttpRequest request = httpClient.createPostRequest("/highscores",
                "{ \"levelId\": " + levelId + ", \"username\": \"" + username + "\", \"time\": " + time + " }");
        httpClient.sendRequest(request, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode == 200) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Error: " + statusCode);
                }
            }

            @Override
            public void failed(Throwable t) {
                callback.onError("Request failed: " + t.getMessage());
            }

            @Override
            public void cancelled() {
                callback.onError("Request cancelled.");
            }
        });
    }

    public void getTopScores(int levelId, int limit, Callback<List<HighScore>> callback) {
        HttpRequest request = httpClient.createGetRequest("/highscores/top/" + levelId + "?limit=" + limit);
        httpClient.sendRequest(request, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode == 200) {
                    String responseJson = httpResponse.getResultAsString();
                    if (responseJson == null || responseJson.equals("{}")) {
                        callback.onSuccess(null);
                        return;
                    }
                    Type listType = new TypeToken<List<HighScore>>() {}.getType();
                    List<HighScore> highScores = gson.fromJson(responseJson, listType);
                    callback.onSuccess(highScores);
                } else {
                    callback.onError("Error: " + statusCode);
                }
            }

            @Override
            public void failed(Throwable t) {
                callback.onError("Request failed: " + t.getMessage());
            }

            @Override
            public void cancelled() {
                callback.onError("Request cancelled.");
            }
        });
    }

    public void getBestScoreForUser(int levelId, String username, Callback<HighScore> callback) {
        HttpRequest request = httpClient.createGetRequest("/highscores/best/" + levelId + "/" + username);
        httpClient.sendRequest(request, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode == 200) {
                    String responseJson = httpResponse.getResultAsString();
                    if (responseJson == null || responseJson.equals("{}")) {
                        callback.onSuccess(null);
                        return;
                    }
                    HighScore highScore = gson.fromJson(responseJson, HighScore.class);
                    callback.onSuccess(highScore);
                } else {
                    callback.onError("Error: " + statusCode);
                }
            }

            @Override
            public void failed(Throwable t) {
                callback.onError("Request failed: " + t.getMessage());
            }

            @Override
            public void cancelled() {
                callback.onError("Request cancelled.");
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
}
