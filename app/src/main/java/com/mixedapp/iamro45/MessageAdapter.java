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

class MessageAdapter extends ArrayAdapter<MixedMessage>{
    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<MixedMessage> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        MixedMessage message = getItem(position);

        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message.getText());
        nameTextView.setText(message.getNumber());
        dateTextView.setText(message.getDate());

        return convertView;
    }
}
