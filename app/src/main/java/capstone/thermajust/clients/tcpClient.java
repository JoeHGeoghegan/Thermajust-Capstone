package capstone.thermajust.clients;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Joe Geoghegan on 5/10/2016.
 */
public class tcpClient {
    private String serverMessage;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    String ip;
    int port;
    public boolean connected;

    public tcpClient(String ip, int port, OnMessageReceived listener) {
        this.ip = ip;
        this.port = port;
        mMessageListener = listener;
    }

    public boolean open() throws Exception {
        return false;
    }
    public boolean close() {
        mRun = false;
        return true;
    }
    public void send(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
    public void run() {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(ip);

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, port);

            try {
                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
