package com.example.Nintenbro;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by student on 4/1/14.
 */
public class ControllerFragment extends Fragment {
    public static final String PREF_KEY_IP_ADDRESS = "pref_key_host_ip";
    public static final String PREF_KEY_HOST_PORT = "pref_key_host_port";

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        //Ensure that the settings contain a port and ip address
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
//        String ipAddress = prefs.getString(PREF_KEY_IP_ADDRESS, "");
//        String port = prefs.getString(PREF_KEY_HOST_PORT, "");
//        if (ipAddress.equals("") || port.equals("")) {
//            Intent intent = new Intent(activity, PreferenceActivity.class);
//            startActivity(intent);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Grab the IP and port from preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UPDsender1.setPort(Integer.parseInt(prefs.getString(PREF_KEY_HOST_PORT, "2390")));
        UPDsender1.setIpAddress(prefs.getString(PREF_KEY_IP_ADDRESS, "127.0.0.0"));

        View viewTreeRoot = inflater.inflate(R.layout.controller_fragment, container, false);

        // Set callbacks for all buttons

    // Set callbacks for all buttons
    Button powerButton = (Button) viewTreeRoot.findViewById(R.id.Power_button);
    powerButton.setActivated(false);
    powerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View powerButton) {
            if (UPDsender1.isRunning()) {
                UPDsender1.isRunning= (false);
                powerButton.setBackgroundResource(R.drawable.power);
            }
            else {
                UPDsender1.setRunningState(true);
                powerButton.setBackgroundResource(R.drawable.power);
                UPDsender1.beginUdpLoop();
                UPDsender1.authenticate = true;
                UPDsender1.authenticate = false;
                // Start another async task loop

            }
        }
    });

        Button upButton = (Button) viewTreeRoot.findViewById(R.id.Up_button);
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction () ) {
                    case MotionEvent.ACTION_DOWN:
                        UPDsender1.forward = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UPDsender1.forward = false;
                        UPDsender1.stop = true;
                        break;
                }
                return true;
            }

        });

        Button leftButton = (Button) viewTreeRoot.findViewById(R.id.Left_button);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UPDsender1.left = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UPDsender1.left = false;
                        UPDsender1.realign = true;
                        break;
                }
                return true;
            }

        });

        Button rightButton = (Button) viewTreeRoot.findViewById(R.id.Right_button);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UPDsender1.right = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UPDsender1.right = false;
                        UPDsender1.realign = true;
                        break;
                }
                return true;
            }

        });



        return viewTreeRoot;
    }

}