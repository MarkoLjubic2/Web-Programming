import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.out;

public class Server implements Runnable{

    private final Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)
        ) {

            String username = loginClient(in, out);

            notifyClients(username, " has joined the chat.");

            showHistory(out);

            readAndProcessMessages(in, username);


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loginClient(BufferedReader in, PrintWriter out) throws IOException {
        String username;
        while (true) {

            username = in.readLine();

            if (!username.isBlank() && !containsUsernameIgnoreCase(username)) {
                Main.clientsMap.put(username, this.socket);
                out.println("Welcome to the chat server!");
                break;
            } else if (username.isBlank()) {
                out.println("Username cannot be blank. Please try again.");
                continue;
            }
            out.println("Username already exists. Please try again.");
        }
        return username;
    }

    private boolean containsUsernameIgnoreCase(String username) {
        return Main.clientsMap.keySet().stream()
                .anyMatch(existingUsername -> existingUsername.equalsIgnoreCase(username));
    }

    private void notifyClients(String username, String message) throws IOException {
        out.println(username + message);
        for (Socket socket : Main.clientsMap.values()) {
            if(socket != this.socket) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                out.println(username + message);
            }
        }
    }

    private void showHistory(PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        for (String message : Main.messages) {
            sb.append(message).append("\n");
        }
        out.println(sb);
    }

    private void readAndProcessMessages(BufferedReader in, String username) throws IOException {
        String message;
        while (true){
            try {
                message = in.readLine();
            } catch (SocketException e) {
                Main.clientsMap.remove(username);
                notifyClients(username, " has left the chat.");
                break;
            }
            message = censorMessage(message);
            out.println(message);
            Main.messages.add(message);
            for(Socket socket : Main.clientsMap.values()) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                out.println(message);
            }
        }
    }

    private String censorMessage(String message) {
        for (String censoredWord : Main.censoredWords) {
            if (message.contains(censoredWord)) {
                String replacement = censoredWord.charAt(0) +
                                     "*".repeat(censoredWord.length() - 2) +
                                     censoredWord.charAt(censoredWord.length() - 1);

                message = message.replaceAll(censoredWord, replacement);
            }
        }
        return message;
    }


}
