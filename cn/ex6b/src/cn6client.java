import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class cn6client {
    public static void main(String args[]) {
        try {
            DatagramSocket client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName("127.0.0.1");

            byte[] sendByte;
            byte[] receiveByte = new byte[1024];

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the Physical Address (MAC): ");
            String mac = in.readLine();

            sendByte = mac.getBytes();
            DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, 1309);
            client.send(sender);

            DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
            client.receive(receiver);

            String ip = new String(receiver.getData(), 0, receiver.getLength()).trim();
            System.out.println("The Logical Address (IP) is: " + ip);

            client.close();
        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
