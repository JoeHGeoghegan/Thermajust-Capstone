package capstone.thermajust.ListAdapterElements;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.thermajust.R;

/**
 * Created by Joe Geoghegan on 2/18/2016.
 */
public class CA_power_read extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    node.powerRead powerNode = null;
    int i=0;

    /*************  CA_group_checklist Constructor *****************/
    public CA_power_read(Activity activity, ArrayList arrayList, Resources resources) {

        /********** Take passed values **********/
        activity = activity;
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
        public TextView read;
        public TextView symbol;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.power_row_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.textView_power_row_name);
            holder.read = (TextView) vi.findViewById(R.id.textView_power_row_meter);
            holder.symbol = (TextView) vi.findViewById(R.id.textView_power_row_symbol);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()>0) {
            /***** Get each Model object from Arraylist ********/
            powerNode =null;
            powerNode = (node.powerRead) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.name.setText(powerNode.getName());
            holder.read.setText(powerNode.getRead());
            holder.symbol.setText(powerNode.getSymbol());


//            holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    CheckBox cb = (CheckBox) v ;
//                    groupCheck.setChecked(cb.isChecked());
//                }
//            });


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        else
            holder.name.setText("No Data");
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CA_power_read", "=====Row button clicked=====");
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
