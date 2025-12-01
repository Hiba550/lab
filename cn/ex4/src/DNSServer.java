import java.net.*;
import java.util.HashMap;

public class DNSServer {

    public static void main(String[] args) {
        HashMap<String, String> dnsTable = new HashMap<>();

        dnsTable.put("google.com", "142.250.183.206");
        dnsTable.put("yahoo.com", "98.137.11.163");
        dnsTable.put("openai.com", "104.18.30.162");

        try {
            DatagramSocket serverSocket = new DatagramSocket(53535);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            System.out.println("DNS Server is running on port 53535...");

            while (true) {
                // Receive domain name from client
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String domain = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Query received for: " + domain);

                // Lookup domain
                String ip = dnsTable.getOrDefault(domain, "Domain not found");

                // Send IP back to client
                sendBuffer = ip.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer, sendBuffer.length, clientAddress, clientPort);

                serverSocket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
