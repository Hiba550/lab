import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 4444);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader keyboard = new BufferedReader(
                     new InputStreamReader(System.in))) {

            System.out.println("Connected to server!");
            String serverMsg, clientMsg;

            while (true) {
                System.out.print("You: ");
                clientMsg = keyboard.readLine();
                out.println(clientMsg);

                if (clientMsg.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }

                serverMsg = in.readLine();
                if (serverMsg == null || serverMsg.equalsIgnoreCase("exit")) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server: " + serverMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
