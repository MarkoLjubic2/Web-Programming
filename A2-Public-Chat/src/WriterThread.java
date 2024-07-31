import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WriterThread implements Runnable{

    private final PrintWriter out;
    private final String username;

    public WriterThread(PrintWriter out, String userName){
        this.out = out;
        this.username = userName;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        String message;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        while (true) {
            message = sc.nextLine();
            if(!message.isBlank())
                out.println(timeFormatter.format(LocalDateTime.now()) + " - " + username + ": " + message);
        }

    }
}
