package capstone.thermajust.Comms;
import java.io.*;
import java.net.*;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public class tcpClient extends client{
    Socket clientSocket;

    public tcpClient() {
        connected = open();
    }

    public boolean open() {
        try {
            clientSocket = new Socket("127.0.0.1", 6789);
            return true;
        }catch (UnknownHostException e1) {
            e1.printStackTrace();
            return false;
        }catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }
    public boolean close() {
        if (connected) {
            try {
                clientSocket.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            return true;
        }
    }
    public void send(String message) {
        if (connected) {
            try {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeBytes(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    public void listen() {
        if (connected) {

        }
    }

    public String getName() {
        return null;
    }
}