import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Server {

    public static void main(String args[]) {
        try (DatagramSocket server = new DatagramSocket(1309)) {
            System.out.println("Server is running on port 1309...");
            String[] ip = { "150.16.20.01", "172.16.9.17" };
            String[] mac = { "6A:08:AA:C2", "8A:BC:E3:FA" };

            while (true) {
                byte[] receivebyte = new byte[1024];
                DatagramPacket receiver = new DatagramPacket(receivebyte, receivebyte.length);
                server.receive(receiver);

                String requestedIp = new String(receiver.getData(), 0, receiver.getLength()).trim();
                InetAddress addr = receiver.getAddress();
                int port = receiver.getPort();

                System.out.println("Received request for IP: " + requestedIp);

                boolean found = false;
                byte[] sendbyte;
                for (int i = 0; i < ip.length; i++) {
                    if (requestedIp.equals(ip[i])) {
                        sendbyte = mac[i].getBytes();
                        server.send(new DatagramPacket(sendbyte, sendbyte.length, addr, port));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    sendbyte = "Address not found".getBytes();
                    server.send(new DatagramPacket(sendbyte, sendbyte.length, addr, port));
                }
            }

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
