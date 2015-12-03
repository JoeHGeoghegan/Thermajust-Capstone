package capstone.thermajust.ControlFragments;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.thermajust.Base_Controller;
import capstone.thermajust.Main_Tabbed_View;
import capstone.thermajust.Model.Device;
import capstone.thermajust.R;
import capstone.thermajust.bluetooth_connect;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ControlFrag_Thermometer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ControlFrag_Thermometer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFrag_Thermometer extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ControlFrag_Thermometer.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFrag_Thermometer newInstance(Device device) {
        ControlFrag_Thermometer fragment = new ControlFrag_Thermometer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ControlFrag_Thermometer() {
        // Required empty public constructor
    }

    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_base_controller, container, false);

        if (getArguments() != null) {

        }

        Button upTemp = (Button) rootView.findViewById(R.id.button_thermfrag_temp_up);
        upTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        Button downTemp = (Button) rootView.findViewById(R.id.button_thermfrag_temp_down);
        downTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        Button updateTemp = (Button) rootView.findViewById(R.id.button_thermfrag_tempUpdate);
        updateTemp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateCurrentTemperature();
            }
        });

        final TextView currentTemp = (TextView) rootView.findViewById(R.id.textView_thermfrag_temp);
        updateCurrentTemperature();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control_thermometer, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void updateCurrentTemperature() {
//        bluetooth_connect.sendText = "TEMP GET";
//        bluetooth_connect.sendData();
//        currentTemp.setText();
    }
}
