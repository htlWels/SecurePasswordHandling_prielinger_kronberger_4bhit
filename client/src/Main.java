import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        EncryptionManager manager = new EncryptionManager();
        manager.initFromStrings();
        Scanner sc = new Scanner(System.in);


        try (   Socket serverSocket = new Socket("localhost",2000);
                Scanner in = new Scanner(serverSocket.getInputStream());
                PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true))
        {
            System.out.println("Verbindung zum Server hergestellt!");


            while (true) {
                System.out.printf("\nGeben Sie Ihren Benutzernamen ein: ");
                String username = sc.next();

                System.out.printf("\nGeben Sie Ihr Passwort ein: ");
                String pwd = sc.next();

                String encryptedPwd = manager.encrypt(pwd);

                System.out.println("\nBenutzername: " + username + "\nUnverschlüsseltes Passwort: " + pwd + "\nVerschlüsselte Passwort: " + encryptedPwd);
                out.println(username);
                out.println(encryptedPwd);
                out.flush();

                String response = in.nextLine();
                System.out.println(response);
            }

        }
        catch (IOException e)
        {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
