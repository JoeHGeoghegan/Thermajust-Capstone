package capstone.thermajust.Comms;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public class tcpClient extends client{
    Socket clientSocket;
    Scanner scanner;
    static Thread workerThread;
    static volatile boolean stopWorker;

    public boolean open() { return false; }
    public tcpClient(String ip, int port) {
        connected = open(ip, port);
    }

    public boolean open(String ip, int port) {
        try {

            clientSocket = new Socket(ip, port);
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
                workerThread.interrupt();
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
//        if (connected) {
//            try {
//                txt = "buffering";
//                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                while (txt.compareTo("buffering") != 0) {
//                    txt = inFromServer.readLine();
//                }
//                clientSocket.close();
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
//    public void listenFor(final String message) {
//        if (connected) {
//
//        }
//    }

    public String getName() {
        return null;
    }
}