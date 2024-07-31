package http;

import app.Quote;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainServer {

    public static final Gson gson = new Gson();
    public static List<Quote> quotes = new CopyOnWriteArrayList<>();
    public static Quote quoteOfTheDay;


    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(9001);
            while (true) {
                Socket socket = ss.accept();
                new Thread(new MainServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Quote getQuoteOfTheDay() throws IOException {
        Socket socket = new Socket("localhost", 9002);
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String getRequest = "GET / HTTP/1.1\r\n" + "Host: localhost:9002\r\n" +"\r\n";

        System.out.println(getRequest);

        socketOut.println(getRequest);

        String responseLine = socketIn.readLine();

        while (!responseLine.trim().isEmpty()) {
            responseLine = socketIn.readLine();
            System.out.println(responseLine);
        }

        String responseBody = socketIn.readLine();

        return MainServer.gson.fromJson(responseBody, Quote.class);
    }

}
