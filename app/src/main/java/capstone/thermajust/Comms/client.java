package capstone.thermajust.Comms;

import java.util.concurrent.ExecutionException;

/**
 * Created by Joe Geoghegan on 3/22/2016.
 */
public abstract class client {
    public boolean connected;
    abstract public boolean open() throws Exception;
    abstract public boolean close();
    abstract public void send(String message);
    abstract public void listen();
    abstract public String getName();
}
