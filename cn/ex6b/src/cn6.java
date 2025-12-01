import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class cn6 {
    public static void main(String args[]) {
        try {
            DatagramSocket server = new DatagramSocket(1309);
            System.out.println("RARP Server is running...");

            String[] mac = { "D4:3D:7E:12:A3:D9" };
            String[] ip = { "10.0.3.186" };

            while (true) {
                byte[] receiveByte = new byte[1024];
                DatagramPacket receiver = new DatagramPacket(receiveByte, receiveByte.length);
                server.receive(receiver);

                String macAddress = new String(receiver.getData(), 0, receiver.getLength()).trim();
                InetAddress addr = receiver.getAddress();
                int port = receiver.getPort();

                System.out.println("Received MAC: " + macAddress);

                boolean found = false;
                byte[] sendByte;

                for (int i = 0; i < mac.length; i++) {
                    if (macAddress.equals(mac[i])) {
                        sendByte = ip[i].getBytes();
                        DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, port);
                        server.send(sender);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    sendByte = "IP Address not found".getBytes();
                    DatagramPacket sender = new DatagramPacket(sendByte, sendByte.length, addr, port);
                    server.send(sender);
                }
            }
        } catch (Exception e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
