package capstone.thermajust.Model;

/**
 * Created by Joe Geoghegan on 11/5/2015.
 *
 * The thermometer class is created to be used in conjunction with the rest of the Thermajust code files.
 *
 */
public class thermometer {
    int setTemp;

    public thermometer(int setTemp) {
        this.setTemp = setTemp;
    }

    //this could take in tokens which are the collection of a thermometers attributes
        //on creation
//    public thermometer(int setTemp) {
//        this.setTemp = setTemp;
//    }

    @Override
    public String toString() {
        return "" + setTemp; // separate values with ,
    }
}
