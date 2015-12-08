package capstone.thermajust.Model;

/**
 * Created by Joe Geoghegan on 11/5/2015.
 * <p/>
 * The thermometer class is created to be used in conjunction with the rest of the Thermajust code files.
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

    public int getSetTemp() { return setTemp; }
    public void setSetTemp(int setTemp) { this.setTemp = setTemp; }
    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }
    public void setMode(String mode) {
        if (mode.compareTo("cool") == 0){
            this.mode = cool;
        } else if (mode.compareTo("heat") == 0){
            this.mode = heat;
        } else {
            this.mode = coolheat;
        }
    }
    @Override
    public String toString() {
        return "" + setTemp + "," + override + "," + mode;
    }
}
