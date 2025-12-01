import java.util.Scanner;

public class CRC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter data bits: ");
        String dataStr = sc.nextLine().trim();

        System.out.print("Enter divisor bits: ");
        String divisorStr = sc.nextLine().trim();

        int dataLen = dataStr.length();
        int divisorLen = divisorStr.length();

        int[] data = new int[dataLen + divisorLen - 1];
        for (int i = 0; i < dataLen; i++)
            data[i] = dataStr.charAt(i) - '0';

        int[] divisor = new int[divisorLen];
        for (int i = 0; i < divisorLen; i++)
            divisor[i] = divisorStr.charAt(i) - '0';

        System.out.print("Dividend (after appending 0's): ");
        for (int bit : data)
            System.out.print(bit);
        System.out.println();

        int[] remainder = divide(data.clone(), divisor);

        int[] crcCode = new int[dataLen + divisorLen - 1];
        for (int i = 0; i < dataLen; i++)
            crcCode[i] = dataStr.charAt(i) - '0';
        for (int i = 0; i < divisorLen - 1; i++)
            crcCode[dataLen + i] = remainder[dataLen + i];

        System.out.print("Transmitted CRC code: ");
        for (int bit : crcCode)
            System.out.print(bit);
        System.out.println();

        System.out.print("Enter received code: ");
        String recvStr = sc.nextLine().trim();

        int[] received = new int[recvStr.length()];
        for (int i = 0; i < recvStr.length(); i++)
            received[i] = recvStr.charAt(i) - '0';

        int[] checkRem = divide(received.clone(), divisor);

        boolean error = false;
        for (int i = recvStr.length() - (divisorLen - 1); i < recvStr.length(); i++) {
            if (checkRem[i] != 0) {
                error = true;
                break;
            }
        }

        if (error)
            System.out.println("Error detected in received data!");
        else
            System.out.println("No Error detected. Transmission successful!");

        sc.close();
    }

    static int[] divide(int[] dividend, int[] divisor) {
        int cur = 0;
        while ((dividend.length - cur) >= divisor.length) {
            if (dividend[cur] == 1) {
                for (int i = 0; i < divisor.length; i++)
                    dividend[cur + i] ^= divisor[i];
            }
            cur++;
        }
        return dividend;
    }
}
