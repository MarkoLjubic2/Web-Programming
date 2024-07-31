import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ReaderThread implements Runnable {

    private final BufferedReader in;

    public ReaderThread(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String receivedMessage;
            while (true) {
                receivedMessage = in.readLine();
                if (!receivedMessage.isBlank())
                    System.out.println(receivedMessage);
            }
        } catch (SocketException e) {
            System.out.println("Connection closed by the server.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}