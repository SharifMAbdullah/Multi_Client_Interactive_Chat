import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerClass {
    private static final int PORT = 43200;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        Socket client = null;

        try {
            while(true) {
                System.out.println("Waiting for connection...");
                client = listener.accept();
                System.out.println("Connection established");
                ClientHandler clientThread = new ClientHandler(client, clients);
                clients.add(clientThread);

                pool.execute(clientThread);
            }
        }catch (IIOException e){
            e.printStackTrace();
        }

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        try {
            while (true){
                String request = in.readLine();
                if(request.contains("date"))
                    out.println(new Date().toString());
                else
                    out.println("Request date please");
            }
        }finally {
            System.out.println("Sent date. Now Closing");
            in.close();
            out.close();
            client.close();
            listener.close();
        }

    }

}
