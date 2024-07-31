import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static Map<String, Socket> clientsMap = new ConcurrentHashMap<>();
    public static Queue<String> messages = new LinkedBlockingQueue<>(100);
    public static List<String> censoredWords = List.of("slayer", "offender", "vulgar");

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(9000)){

            while (true) {
                Socket socket = serverSocket.accept();
                Thread serverThread = new Thread(new Server(socket));
                serverThread.start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }


    }

}
