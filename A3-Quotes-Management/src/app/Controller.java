package app;

import http.HttpRequest;
import http.response.Response;

import java.io.IOException;

public abstract class Controller {

    protected HttpRequest request;

    public Controller(HttpRequest request) {
        this.request = request;
    }

    public abstract Response doGet() throws IOException;
    public abstract Response doPost();
}