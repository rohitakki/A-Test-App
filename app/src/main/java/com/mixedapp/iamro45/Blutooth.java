package com.mixedapp.iamro45;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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
public class Blutooth extends Fragment {

    private View mView;
    private Button bluetoothOnButton, bluetoothOffButton;
    private ImageView bluetoothOnImage, bluetoothOffImage;
    private BluetoothAdapter bluetoothAdapter;

    public Blutooth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_blutooth, container, false);

        bluetoothOffButton = mView.findViewById(R.id.bluetooth_off_button);
        bluetoothOnButton = mView.findViewById(R.id.bluetooth_on_button);
        bluetoothOnImage = mView.findViewById(R.id.bluetooth_on_image);
        bluetoothOffImage = mView.findViewById(R.id.bluetooth_off_image);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter.isEnabled()){
            bluetoothOffImage.setVisibility(View.INVISIBLE);
            bluetoothOnImage.setVisibility(View.VISIBLE);
        }else {
            bluetoothOnImage.setVisibility(View.INVISIBLE);
            bluetoothOffImage.setVisibility(View.VISIBLE);
        }

        bluetoothOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetoothAdapter.isEnabled()){
                    Toast.makeText(getContext(), "Bluetooth is already Enabled", Toast.LENGTH_SHORT).show();
                }else {
                    bluetoothAdapter.enable();
                    bluetoothOffImage.setVisibility(View.INVISIBLE);
                    bluetoothOnImage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bluetoothOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bluetoothAdapter.isEnabled()){
                    Toast.makeText(getContext(), "Bluetooth is already Disabled", Toast.LENGTH_SHORT).show();
                }else {
                    bluetoothAdapter.disable();
                    bluetoothOnImage.setVisibility(View.INVISIBLE);
                    bluetoothOffImage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Bluetooth Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mView;
    }
}