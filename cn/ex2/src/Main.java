import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        String host = "example.org";
        int port = 80;

        System.out.println("Connecting to " + host + " on port " + port + "...");

        try (Socket socket = new Socket(host, port);
             PrintWriter pw = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), "UTF-8"))) {

            // Send HTTP request
            pw.print("GET / HTTP/1.1\r\n");
            pw.print("Host: " + host + "\r\n");
            pw.print("Connection: close\r\n\r\n");
            pw.flush();

            System.out.println("---- Response from " + host + " ----");

            // Read and print response
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("---- End of Response ----");

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + host);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
