package capstone.thermajust.Model;

/**
 * Created by Joe Geoghegan on 11/5/2015.
 *
 * The thermometer class is created to be used in conjunction with the rest of the Thermajust code files.
 *
 */
public class thermometer {
    int setTemp;
    boolean override;
    int mode;

    final int cool = 1; //if the device can cool
    final int heat = 2; //if the device can heat
    final int coolheat = 3; //if the device can do both

    public thermometer(int setTemp, boolean override, int mode) {
        this.setTemp = setTemp;
        this.override = override;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "" + setTemp + "," + override + "," + mode;
    }
}
