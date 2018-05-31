package com.mixedapp.iamro45;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Call extends Fragment {

    private static final int REQUEST_CODE = 123;
    private static final int REQUEST_PERMISSSION_CALL = 123;
    private View mView;
    private EditText numberEditText;
    private TextView callTextView;
    private ImageButton contactImageButton;
    private ArrayList<String> numberArray = new ArrayList<String>();
    private ArrayList<String> timeArray = new ArrayList<String>();
    private ListView logList;
    private DateFormat timeFormat;
    private String date;

    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;

    private LogsAdapter mLogsAdapter;
    private ListView mLogsList;


    public Call() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_call, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Mixed").child("Logs");

        numberEditText = mView.findViewById(R.id.number_editText);
        callTextView = mView.findViewById(R.id.call_textView);
        contactImageButton = mView.findViewById(R.id.contact_button);

        mLogsList = mView.findViewById(R.id.list);
        List<MixedLogs> mixedLogs = new ArrayList<>();
        mLogsAdapter = new LogsAdapter(getContext(), R.layout.liststyle, mixedLogs);
        mLogsList.setAdapter(mLogsAdapter);

        Collections.reverse(numberArray);
        Collections.reverse(timeArray);

        callTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberEditText.getText().toString();
                if(number.isEmpty()){
                    Toast.makeText(getContext(), "please enter a number to call", Toast.LENGTH_SHORT).show();
                }else {
                    call(number);
                }
            }
        });

        contactImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(pickContact, REQUEST_CODE);
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MixedLogs logs = dataSnapshot.getValue(MixedLogs.class);
                mLogsAdapter.add(logs);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.addChildEventListener(mChildEventListener);

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            Uri contactUri = data.getData();
            Cursor nameCursor = getActivity().getContentResolver().query(contactUri, null, null, null, null);
            if(nameCursor.moveToFirst()){
                String mPhone = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                numberEditText.setText(mPhone);
                nameCursor.close();
            }
        }else {
            Toast.makeText(getContext(), "No contact chosen!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+phone));
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSSION_CALL);
            call(phone);
        }else {
            timeFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            date = timeFormat.format(Calendar.getInstance().getTime());

            startActivity(intent);
            MixedLogs mixedLogs = new MixedLogs(phone, date);
            mDatabase.push().setValue(mixedLogs);
            numberEditText.setText("");
        }
    }
}