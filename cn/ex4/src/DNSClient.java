import java.net.*;
import java.util.Scanner;

public class DNSClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");

            while (true) {
                System.out.print("Enter domain name (or 'exit' to quit): ");
                String domain = sc.nextLine();

                if (domain.equalsIgnoreCase("exit")) {
                    break;
                }

                // Send query to server
                byte[] sendBuffer = domain.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer, sendBuffer.length, serverAddress, 53535);
                clientSocket.send(sendPacket);

                // Receive response from server
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String ip = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("IP Address: " + ip);
            }

            clientSocket.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
