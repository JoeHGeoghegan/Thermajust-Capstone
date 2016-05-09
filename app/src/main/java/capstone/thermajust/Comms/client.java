package capstone.thermajust.Comms;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.ExecutionException;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public abstract class client {
//    public Activity activity;
    public static Context context;
//    public String txt = "";
    public boolean connected;
    abstract public boolean open() throws Exception;
    abstract public boolean close();
    abstract public void send(String message);
    abstract public void listen();
    abstract public String getName();
}
