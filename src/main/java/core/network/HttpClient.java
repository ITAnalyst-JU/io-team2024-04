package core.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;

public class HttpClient {
    private final String serverUrl;

    public HttpClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void sendRequest(HttpRequest request, HttpResponseListener listener) {
        Gdx.net.sendHttpRequest(request, listener);
    }

    public HttpRequest createPostRequest(String path, String jsonData) {
        HttpRequest request = new HttpRequest(Net.HttpMethods.POST);
        request.setUrl(serverUrl + path);
        request.setHeader("Content-Type", "application/json");
        request.setContent(jsonData);
        return request;
    }

    public HttpRequest createGetRequest(String path) {
        HttpRequest request = new HttpRequest(Net.HttpMethods.GET);
        request.setUrl(serverUrl + path);
        return request;
    }
}
