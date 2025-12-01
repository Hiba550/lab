import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final int PORT = 4444;

    public static void main(String[] args) {
        System.out.println("[SERVER] Starting on port " + PORT + " ...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Waiting for client...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("[SERVER] Client connected: " +
                        clientSocket.getRemoteSocketAddress());

                String line;
                while ((line = in.readLine()) != null) {
                    if ("-1".equals(line)) {
                        System.out.println("[SERVER] Client requested to end session.");
                        break; 
                    }

                    System.out.println("[SERVER] Received: " + line);
                    out.println(line);
                }

                System.out.println("[SERVER] Closing connection...");
            }

        } catch (IOException e) {
            System.err.println("[SERVER] Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("[SERVER] Stopped.");
    }
}
