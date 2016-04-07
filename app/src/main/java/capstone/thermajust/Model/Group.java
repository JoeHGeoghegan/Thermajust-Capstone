package capstone.thermajust.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joe Geoghegan on 10/11/2015.
 * <p/>
 * The Group class is created to be used in conjunction with the rest of the Thermajust code files.
 * <p/>
 * Stores a list of Devices and their properties for quick access
 * Has helper arrays which hold the location of different types of devices so that quick
 * settings can be done quickly and only to the devices that the operation can be done to.
 */
public class Group {
    public String name;
    public ArrayList<Device> devices;
    private ArrayList<Integer> tempEnabled = new ArrayList<>();
    private ArrayList<Integer> micEnabled = new ArrayList<>();
    private ArrayList<Integer> vidEnabled = new ArrayList<>();


    public Group(String name, ArrayList<Device> devices) {
        this.name = name;
        this.devices = devices;

        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getUseTemp()) {
                tempEnabled.add(i);
            }
            if (devices.get(i).getUseMic()) {
                micEnabled.add(i);
            }
            if (devices.get(i).getUseVid()) {
                vidEnabled.add(i);
            }

        }
    }

    public int findDevicePos(Device device) {
        if (devices.contains(device)) {
            for (int i = 0; i < devices.size() ; i++) {
                if (devices.get(i) == device) {
                    return i;
                }
            }
        }
        return -1; //not in array
    }

    //Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Integer> getVidEnabled() { return vidEnabled; }
    public void setVidEnabled(ArrayList<Integer> vidEnabled) { this.vidEnabled = vidEnabled; }

    public ArrayList<Integer> getTempEnabled() { return tempEnabled; }
    public void setTempEnabled(ArrayList<Integer> tempEnabled) { this.tempEnabled = tempEnabled; }

    public ArrayList<Integer> getMicEnabled() { return micEnabled; }
    public void setMicEnabled(ArrayList<Integer> micEnabled) { this.micEnabled = micEnabled; }

    @Override
    public String toString() {
        String write = getName() + ","
                + devices.size() + ",";
        for (int i = 0; i < devices.size(); i++) {
            write = write + devices.get(i).getIdNum() + ",";
        }
        return write + "<!>end<!>\n";
    }
}
