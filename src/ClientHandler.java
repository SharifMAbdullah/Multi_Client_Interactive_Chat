import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }
    @Override
    public void run() {
        try {
            while (true){
                String request = in.readLine();
                if(request.contains("date"))
                    out.println(new Date().toString());
                else if(request.startsWith("say")){
                    int firstSpace = request.indexOf(" ");
                    if(firstSpace!=-1)
                        outToAll(request.substring(firstSpace+1));
                }
                else
                    out.println("Request date please");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Sent date. Now Closing");
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }

    }

    private void outToAll(String message) {
        for(ClientHandler aClient : clients){
            aClient.out.println(message);
        }
    }
}
