package com.mixedapp.iamro45;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Blutooth blutooth = new Blutooth();
                return blutooth;

            case 1:
                WiFi wiFi = new WiFi();
                return wiFi;

            case 2:
                Message message = new Message();
                return message;

            case 3:
                Call call = new Call();
                return call;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "Bluetooth";

            case 1:
                return "WiFi";

            case 2:
                return "Message";

            case 3:
                return "Calls";

            default:
                return null;
        }
    }
}
