import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Client {

    public static void main(String args[]) {
        try (DatagramSocket client = new DatagramSocket();
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            InetAddress addr = InetAddress.getByName("127.0.0.1");
            byte[] sendbyte;
            byte[] receivebyte = new byte[1024];

            System.out.print("Enter the logical address (IP): ");
            String str = in.readLine();
            sendbyte = str.getBytes();

            DatagramPacket sender = new DatagramPacket(sendbyte, sendbyte.length, addr, 1309);
            client.send(sender);

            DatagramPacket receiver = new DatagramPacket(receivebyte, receivebyte.length);
            client.receive(receiver);

            String s = new String(receiver.getData(), 0, receiver.getLength()).trim();
            System.out.println("The Physical Address (MAC): " + s);

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
