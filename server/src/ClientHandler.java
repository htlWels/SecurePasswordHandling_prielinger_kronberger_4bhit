import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        RSA rsa = new RSA();
        rsa.initFromStrings();

        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            Scanner in = new Scanner(clientSocket.getInputStream()))
        {

            while (clientSocket.isConnected()) {
                String username = in.nextLine();
                String pwd = in.nextLine();
                String decryptedPwd = rsa.decrypt(pwd);

                System.out.println("\nBenutzername: " + username + "\nVerschlüsseltes Passwort: " + pwd + "\nUnverschlüsselte Passwort: " + decryptedPwd);

                if (username.equals("user") && decryptedPwd.equals("user"))
                    out.println("Korrekter Login!");

                else
                    out.println("Falscher Benutzer oder Passwort!");
            }

            out.flush();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
