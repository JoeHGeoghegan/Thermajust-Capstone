package capstone.thermajust.ListAdapterElements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.thermajust.Edit_Device;
import capstone.thermajust.Joined_Controller;
import capstone.thermajust.Main_Tabbed_View;
import capstone.thermajust.R;

/**
 * Created by Joe Geoghegan on 2/18/2016.
 */
public class CA_device_control extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    node.deviceControl deviceNode = null;
    int i=0;
    static String type;
    static int positionHold;

    /*************  CA_group_checklist Constructor *****************/
    public CA_device_control(Activity activity, ArrayList arrayList, Resources resources) {

        /********** Take passed values **********/
        this.activity = activity;
        data=arrayList;
        res = resources;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView id;
        public ImageButton controlButton;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.device_row_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.textView_device_row_name);
            holder.id = (TextView) vi.findViewById(R.id.textView_device_row_id);
            holder.controlButton = (ImageButton) vi.findViewById(R.id.imageButton_device_row);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()>0) {
            /***** Get each Model object from Arraylist ********/
            deviceNode =null;
            deviceNode = (node.deviceControl) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.name.setText(deviceNode.getName());
            holder.id.setText((deviceNode.getId()));


            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(activity, Edit_Device.class);
                    myIntent.putExtra("selectedDevice", position);
                    activity.startActivity(myIntent);
                }
            });
            holder.id.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(activity, Edit_Device.class);
                    myIntent.putExtra("selectedDevice", position);
                    activity.startActivity(myIntent);
                }
            });
            holder.controlButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    positionHold = position;
                    Intent myIntent = new Intent(activity, Joined_Controller.class);
                    myIntent.putExtra("single", true);
                    if (Main_Tabbed_View.model.deviceList.get(positionHold).getUseTemp()) {
                        myIntent.putExtra("mode", "temp");
                    } else {
                        myIntent.putExtra("mode", "base");
                    }
                    myIntent.putExtra("selection", positionHold);
                    myIntent.putExtra("type", "tcp");
                    activity.startActivity(myIntent);
                }
            });


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        else
            holder.name.setText("No Data");
        return vi;
    }


    @Override
    public void onClick(View v) {
        Log.v("CA_device_control", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


//            CustomListViewAndroidExample sct = (CustomListViewAndroidExample)activity;
//
//            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
//
//            sct.onItemClick(mPosition);
        }
    }
}