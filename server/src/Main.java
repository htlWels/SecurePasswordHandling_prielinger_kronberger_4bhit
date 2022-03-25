import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        System.out.println("Server gestartet!");

        Socket clientSocket = null;
        ExecutorService executorService = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(2000))
        {
            while (true) {
                System.out.println("\nServer ist auf Port 2000 bereit!");

                clientSocket = serverSocket.accept();
                //Kommunikation mit Client
                executorService.execute(new ClientHandler(clientSocket));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            if (clientSocket != null)
            {
                try { clientSocket.close(); } catch (IOException e) {  }
            }
        }
    }
}
