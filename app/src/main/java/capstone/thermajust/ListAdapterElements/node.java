package capstone.thermajust.ListAdapterElements;

import android.widget.Toast;

import capstone.thermajust.Group_Setup;
import capstone.thermajust.Main_Tabbed_View;

/**
 * Created by Joe Geoghegan on 2/18/2016.
 */
public class node {
    /** ON MAIN TABBED VIEW
     * This subclass acts as a buffer between a list and the actual
     * device so function calls can be made easier through this
     */
    public static class deviceControl {
        String name;
        String id;
        int pos;

        public deviceControl(String name, String id, int pos) {
            this.name = name;
            this.id = id;
            this.pos = pos;
        }

        public String getName() { return name; }
        public String getId() { return id; }
    }

    /** ON MAIN TABBED VIEW
     * This subclass acts as a buffer between a list and the actual
     * group so function calls can be made easier through this
     */
    public static class groupControl {
        String name;
        int pos;

        public groupControl(String name, int pos) {
            this.name = name;
            this.pos = pos;
        }

        public String getName() { return name; }
    }

    /** ON GROUP SETUP
     * This subclass acts as a place to hold necessary and the checked information of device list,
     * position is the same as the original device list these will be used on so it does not need
     * to be stored
     */
    public static class groupCheck {
        String name;
        int pos;
        boolean checked;

        public groupCheck(String name, int pos, boolean checked) {
            this.name = name;
            this.pos = pos;
            this.checked = checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
            if (this.checked) {
                Group_Setup.includedDevicesAdd(Main_Tabbed_View.model.deviceList.get(pos));
            } else {
                Group_Setup.includedDevicesRemove(Main_Tabbed_View.model.deviceList.get(pos));
            }
        }

        public boolean getChecked() { return checked; }
        public String getName() { return name; }
    }

    /** ON MAIN TABBED VIEW
     * This subclass acts as a buffer between a list and the actual
     * device so function calls can be made easier through this
     */
    public static class powerRead {
        String name;
        String read;
        String symbol;
        int pos;

        public powerRead(String name, String read, String symbol, int pos) {
            this.name = name;
            this.read = read;
            this.symbol = symbol;
            this.pos = pos;
        }

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public String getRead() { return read; }
    }
}
