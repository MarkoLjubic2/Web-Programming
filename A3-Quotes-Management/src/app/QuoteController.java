package app;

import com.google.gson.Gson;
import http.HttpRequest;
import http.MainServer;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;
import java.io.IOException;

public class QuoteController extends Controller{

    private final Gson gson;

    public QuoteController(HttpRequest request) {
        super(request);
        this.gson = new Gson();
    }

    @Override
    public Response doGet() throws IOException {
        MainServer.quoteOfTheDay = MainServer.getQuoteOfTheDay();
        String content = String.format(
                "<html>" +
                "<head>" +
                        "<title>Server response</title>" +
                "</head>" +
                    "<body>" +
                        "<form method=\"POST\" action=\"/save-quote\">" +
                            "<label>Author: </label><input name=\"author\" type=\"text\"><br><br>" +
                            "<label>Quote: </label><input name=\"quote\" type=\"text\"><br><br>" +
                            "<button>Save Quote</button>" +
                        "</form>" +
                        "<h1>Quote of the day:</h1>\n" +
                        "%s" +
                        "<div \"quotes\">" +
                            "<h1>Saved Quotes:</h1>\n" +
                            "%s" +
                        "</div>" +
                    "</body>" +
                "</html>",
                MainServer.quoteOfTheDay,
                generateSavedQuotesHTML()
        );

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {

        String author = request.getPostParams().get("author");
        String quoteText = request.getPostParams().get("quote");

        Quote quote = new Quote(author, quoteText);

        MainServer.quotes.add(quote);
        System.out.println("Added quote: " + gson.toJson(quote));
        return new RedirectResponse("/quotes");
    }

    private String generateSavedQuotesHTML() {
        StringBuilder sb = new StringBuilder();
        for (Quote quote : MainServer.quotes) {
            sb.append("<p>").append(quote.toString()).append("</p>\n");
        }
        return sb.toString();
    }
}
