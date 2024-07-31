import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 9000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String username = login(in, out);
            Thread writer = new Thread(new WriterThread(out, username));
            Thread reader = new Thread(new ReaderThread(in));
            writer.start();
            reader.start();

            writer.join();
            reader.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String login(BufferedReader in, PrintWriter out){
        String username;
        Scanner scanner = new Scanner(System.in);
        String response;

        do {
            System.out.println("Enter your username:");
            username = scanner.nextLine();
            out.println(username);

            try {
                response = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(response);
        } while (!response.equalsIgnoreCase("Welcome to the chat server!"));

        return username;
    }


}