package core.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponseListener;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HttpClientTest {
    private HttpClient httpClient;
    private Net net;

    @BeforeEach
    public void setUp() {
        net = mock(Net.class);
        httpClient = new HttpClient("http://test.com");
        Gdx.net = net;
    }

    @Test
    public void testCreatePostRequest() {
        HttpRequest request = httpClient.createPostRequest("/path", "{\"key\":\"value\"}");

        assertEquals(Net.HttpMethods.POST, request.getMethod());
        assertEquals("http://test.com/path", request.getUrl());
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
        assertEquals("{\"key\":\"value\"}", request.getContent());
    }

    @Test
    public void testCreateGetRequest() {
        HttpRequest request = httpClient.createGetRequest("/path");

        assertEquals(Net.HttpMethods.GET, request.getMethod());
        assertEquals("http://test.com/path", request.getUrl());
    }

    @Test
    public void testSendRequest() {
        HttpRequest request = new HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://test.com/path");
        HttpResponseListener listener = mock(HttpResponseListener.class);

        httpClient.sendRequest(request, listener);

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        ArgumentCaptor<HttpResponseListener> listenerCaptor = ArgumentCaptor.forClass(HttpResponseListener.class);

        verify(net).sendHttpRequest(requestCaptor.capture(), listenerCaptor.capture());
        assertEquals(request, requestCaptor.getValue());
        assertEquals(listener, listenerCaptor.getValue());
    }
}
