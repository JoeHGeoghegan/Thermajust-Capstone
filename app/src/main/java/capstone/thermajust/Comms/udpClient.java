package capstone.thermajust.Comms;
import java.io.*;
import java.net.*;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public class udpClient extends client{
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    public udpClient() {
        connected = open();
    }

    public boolean open() {
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName("localhost");
            return true;
        } catch (SocketException e1) {
            e1.printStackTrace();
            return false;
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
            return false;
        }
    }
    public boolean close() {
        if (connected) {
            clientSocket.close();
        }
        return true;
    }
    public void send(String message) {
        if (connected) {
            try {
                DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, IPAddress, 9876);
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
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