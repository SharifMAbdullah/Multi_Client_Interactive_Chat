import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable{
    private Socket server;
    private BufferedReader in;
    private PrintWriter out;

    public ServerConnectionHandler(Socket s) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        out = new PrintWriter(server.getOutputStream(), true);


    }
    @Override
    public void run() {

            String serverResponse = "";
            try {
                while (true) {
                    serverResponse = in.readLine();
                    if(serverResponse.equals(""))
                        break;
                    System.out.println("Server says: " + serverResponse);

                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

