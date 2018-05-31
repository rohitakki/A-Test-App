package com.mixedapp.iamro45;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WiFi extends Fragment {

    private static final int REQUEST_WIFI_PERMISSION = 123;
    private static final int REQUEST_WIFI_ACCESS = 123;
    private View mView;
    private Button wifiOnButton, wifiOffButton;
    private ImageView wifiOnImage, wifiOffImage;
    private WifiManager wifiManager;


    public WiFi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_wi_fi, container, false);

        wifiOnImage = mView.findViewById(R.id.wifi_on_image);
        wifiOnButton = mView.findViewById(R.id.wifi_on_button);
        wifiOffImage = mView.findViewById(R.id.wifi_off_image);
        wifiOffButton = mView.findViewById(R.id.wifi_off_button);

        wifiOnImage.setVisibility(View.INVISIBLE);
        wifiOffImage.setVisibility(View.INVISIBLE);

        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(wifiManager.isWifiEnabled()){
                wifiOffImage.setVisibility(View.INVISIBLE);
                wifiOnImage.setVisibility(View.VISIBLE);
            }else {
                wifiOnImage.setVisibility(View.INVISIBLE);
                wifiOffImage.setVisibility(View.VISIBLE);
            }

        wifiOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifiManager.isWifiEnabled()){
                    Toast.makeText(getContext(), "Wi-Fi is already Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    wifiManager.setWifiEnabled(true);
                    wifiOffImage.setVisibility(View.INVISIBLE);
                    wifiOnImage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Wi-Fi Enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        wifiOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!wifiManager.isWifiEnabled()){
                    Toast.makeText(getContext(), "Wi-Fi is already Disabled", Toast.LENGTH_SHORT).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(getContext(), "Wi-Fi Disabled", Toast.LENGTH_SHORT).show();
                    wifiOnImage.setVisibility(View.INVISIBLE);
                    wifiOffImage.setVisibility(View.VISIBLE);
                }
            }
        });

        return mView;
    }
}
