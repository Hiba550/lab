import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            System.out.println("Server started. Waiting for client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader keyboard = new BufferedReader(
                         new InputStreamReader(System.in))) {

                System.out.println("Client connected!");
                String clientMsg, serverMsg;

                while (true) {
                    clientMsg = in.readLine();
                    if (clientMsg == null || clientMsg.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                    System.out.println("Client: " + clientMsg);
                    System.out.print("You: ");
                    serverMsg = keyboard.readLine();
                    out.println(serverMsg);

                    if (serverMsg.equalsIgnoreCase("exit")) {
                        System.out.println("Server closing connection...");
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
