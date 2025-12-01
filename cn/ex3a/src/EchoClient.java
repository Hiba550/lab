import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private static final String HOST = "localhost";
    private static final int PORT = 4444;

    public static void main(String[] args) {
        System.out.println("[CLIENT] Connecting to " + HOST + ":" + PORT + " ...");

        try (Socket socket = new Socket(InetAddress.getByName(HOST), PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner user = new Scanner(System.in)) {

            System.out.println("[CLIENT] Connected. Type messages; use -1 to exit.\n");

            while (true) {
                System.out.print("You> ");
                String msg = user.nextLine().trim();
                out.println(msg);

                if ("-1".equals(msg)) {
                    System.out.println("[CLIENT] Exit requested. Bye!");
                    break;
                }

                String reply = in.readLine();
                if (reply == null) {
                    System.out.println("[CLIENT] Server closed the connection.");
                    break;
                }

                System.out.println("Server> " + reply);
            }

        } catch (IOException e) {
            System.err.println("[CLIENT] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
