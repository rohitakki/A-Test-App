package com.mixedapp.iamro45;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class LogsAdapter extends ArrayAdapter<MixedLogs>{


    public LogsAdapter(@NonNull Context context, int resource, @NonNull List<MixedLogs> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.liststyle, parent, false);
        }

        TextView numberTextView = convertView.findViewById(R.id.log_number);
        TextView dateTextView = convertView.findViewById(R.id.log_date);

        MixedLogs logs = getItem(position);

        numberTextView.setText(logs.getNumber());
        dateTextView.setText(logs.getDate());

        return convertView;
    }
}
