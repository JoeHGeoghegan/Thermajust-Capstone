package capstone.thermajust.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joe Geoghegan on 10/11/2015.
 *
 * The Group class is created to be used in conjunction with the rest of the Thermajust code files.
 *
 * Stores a list of Devices and their properties for quick access
 *      Has helper arrays which hold the location of different types of devices so that quick
 *      settings can be done quickly and only to the devices that the operation can be done to.
 */
public class Group {
    public ArrayList<Device> devices;
    private short[] tempEnabled;
    private short[] micEnabled;
    private short[] vidEnabled;


    public Group(short[] vidEnabled, ArrayList<Device> devices, short[] tempEnabled, short[] micEnabled) {
        this.vidEnabled = vidEnabled;
        this.devices = devices;
        this.tempEnabled = tempEnabled;
        this.micEnabled = micEnabled;
    }

    //toString
        //todo CURRENTLY FORMATTED BADLY (mainly because of devices.toString)
    @Override
    public String toString() {
        return "Group{" +
                "devices=" + devices.toString() +
                ", tempEnabled=" + Arrays.toString(tempEnabled) +
                ", micEnabled=" + Arrays.toString(micEnabled) +
                ", vidEnabled=" + Arrays.toString(vidEnabled) +
                '}';
    }

    //Getters and Setters
    public short[] getVidEnabled() { return vidEnabled; }
    public void setVidEnabled(short[] vidEnabled) { this.vidEnabled = vidEnabled; }
    public short[] getTempEnabled() { return tempEnabled; }
    public void setTempEnabled(short[] tempEnabled) { this.tempEnabled = tempEnabled; }
    public short[] getMicEnabled() { return micEnabled; }
    public void setMicEnabled(short[] micEnabled) { this.micEnabled = micEnabled; }
}
