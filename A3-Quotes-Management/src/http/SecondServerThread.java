package http;

import app.Quote;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class SecondServerThread implements Runnable{

    private final Socket client;
    private static final Gson gson = new Gson();

    public SecondServerThread(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true)
        ) {
            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            if (path.equals("/favicon.ico"))
                return;

            System.out.println("\nHTTP REQUEST:\n");

            do {
                System.out.println(requestLine);

                requestLine = in.readLine();
            } while (!requestLine.trim().isEmpty());

            if (method.equals(HttpMethod.GET.toString())) {
                Quote randomQuote = SecondServer.getQuoteOfTheDay();
                String jsonQuote = gson.toJson(randomQuote);
                String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + jsonQuote;
                out.println(response);

                System.out.println("\nHTTP response:\n" + response);
            }

            client.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
