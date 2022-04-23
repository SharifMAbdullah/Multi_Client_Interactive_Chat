import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientClass {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 43200;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP,SERVER_PORT);

        ServerConnectionHandler serverConnect = new ServerConnectionHandler(socket);

        BufferedReader inputFromKeyBoard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(serverConnect).start();
            while(true) {

                System.out.printf(">");
                String command = inputFromKeyBoard.readLine();

                if(command.equals("quit"))
                    break;

                out.println(command);

            }

            socket.close();
            System.exit(0);



    }
}
