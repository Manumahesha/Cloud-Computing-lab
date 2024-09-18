#client
  
import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            SecretKey key = AESEncryptionUtil.getAESKey();
            String message = "Hello Server...";
            String encryptedMessage = AESEncryptionUtil.encrypt(message, key);
            System.out.println("Encrypted Message: " + encryptedMessage);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(encryptedMessage);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Server Response: " + in.readLine());

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

-------------------------------------------------------------------------------------------------

#server
import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server is running");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String encryptedMessage = in.readLine();
                System.out.println("Received encrypted message: " + encryptedMessage);

                SecretKey key = AESEncryptionUtil.getAESKey();
                try {
                    String decryptedMessage = AESEncryptionUtil.decrypt(encryptedMessage, key);
                    System.out.println("Decrypted Message: " + decryptedMessage);
                    
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Message received and decrypted");

                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
----------------------------------------------------------------------------------------------------------------

#AESEncryption
  
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryptionUtil {
    private static final String AES = "AES";
    private static final String AES_KEY = "1234567890123456"; // Must be 16 bytes for AES-128

    public static SecretKey getAESKey() {
        return new SecretKeySpec(AES_KEY.getBytes(), AES);
    }

    public static String encrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
