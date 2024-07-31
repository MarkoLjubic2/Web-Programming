package app;

import http.HttpMethod;
import http.HttpRequest;
import http.response.Response;

public class RequestHandler {

    public Response handle(HttpRequest request) throws Exception {

        if (request.getPath().equals("/quotes") && request.getHttpMethod().equals(HttpMethod.GET))
            return (new QuoteController(request)).doGet();
        else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HttpMethod.POST))
            return (new QuoteController(request)).doPost();


        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");

    }
}
