package http;

import app.RequestHandler;
import http.response.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainServerThread implements Runnable{

    private final Socket client;

    public MainServerThread(Socket socket) {
        this.client = socket;
    }

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

            System.out.println("\nHTTP CLIENT REQUEST:\n");

            int contentLength = 0;

            do {
                System.out.println(requestLine);
                if (requestLine.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(requestLine.split(":")[1].trim());
                    System.out.println("Content length: " + contentLength);
                }
                requestLine = in.readLine();
            } while (!requestLine.trim().isEmpty());

            Map<String, String> postParams = new HashMap<>();

            if (method.equals(HttpMethod.POST.toString())) {
                char[] buffer = new char[contentLength];
                in.read(buffer);
                String requestBody = new String(buffer);
                System.out.println(requestBody);

                postParams = extractPostParams(requestBody);

                MainServer.quoteOfTheDay = MainServer.getQuoteOfTheDay();
                System.out.println("Quote of the day: " +  MainServer.quoteOfTheDay);
            }


            HttpRequest request = new HttpRequest(HttpMethod.valueOf(method), path, postParams);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP response:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());

            client.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> extractPostParams(String requestBody) {
        Map<String, String> postParams = new HashMap<>();
        String[] params = requestBody.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                postParams.put(key, value);
            }
        }
        return postParams;
    }
}
