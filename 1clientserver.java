#client

import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;
public class client1{
public static void main(String [] args){
try{
Socket socket=new Socket("localhost", 8080);
SecretKey key= AESEncryption.getAESKey();
String Message="Hello, Server.......";

String EncryptedMessage=AESEncryption.encrypt(Message,key);
System.out.println("encrypted message:"+EncryptedMessage);
PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
System.out.println(EncryptedMessage);
BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
System.out.println("Server response:"+in.readLine());
in.close();
out.close();
socket.close();
}
catch(Exception e){ 
e.printStackTrace();
}
}
}
-------------------------------------------------------------------------------------------------

#server
import java.io.*;
import java.net.*;
import javax.crypto.SecretKey;
public class server1{
public static void main(String[] args){
try{
ServerSocket serverSocket=new ServerSocket(8080);
System.out.println("server is running.....");
while(true){
Socket socket=serverSocket.accept();
System.out.println("client connected");
BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
String encryptedMessage=in.readLine();
System.out.println("received encrypted message:"+encryptedMessage);
SecretKey key=AESEncryption.getAESKey();
try{

String decryptedMessage=AESEncryption.decrypt(encryptedMessage,key);
System.out.println("Decrypted message:"+decryptedMessage);
PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
System.out.println("message received and decrypted successfully:");
}
catch(Exception e){
System.out.println("failed to decrypt message");
PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
out.println("only encrypted message are processed:");
}
socket.close();
}}catch(Exception e){
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
public class AESEncryption{
private static final String AES="AES";
private static final String AES_KEY="1234567890123456";
public static SecretKey getAESKey(){
return new SecretKeySpec (AES_KEY.getBytes(),AES);
}
public static String encrypt(String message, SecretKey key) throws Exception{
Cipher cipher=Cipher.getInstance(AES);
cipher.init(Cipher.ENCRYPT_MODE,key);
byte[] encryptedBytes=cipher.doFinal(message.getBytes());
return
Base64.getEncoder().encodeToString(encryptedBytes);
}
public static String decrypt(String encryptedMessage,SecretKey key) throws Exception{
Cipher cipher=Cipher.getInstance(AES);
cipher.init(Cipher.DECRYPT_MODE, key);
byte[] decodedBytes= Base64.getDecoder().decode(encryptedMessage);
byte[] decryptedBytes=cipher.doFinal(decodedBytes);
return new String(decryptedBytes);    
}
}
