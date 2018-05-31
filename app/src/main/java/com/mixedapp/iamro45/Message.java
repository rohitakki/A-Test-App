package com.mixedapp.iamro45;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Message extends Fragment {

    private static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int REQUEST_CODE = 123;
    private int REQUEST_PERMISSSION_SEND_SMS = 123;
    private View mView;
    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private EditText mMessageEditText;
    private Button mSendButton;
    private EditText numberEditText;
    private TextView callTextView;
    private ImageButton contactImageButton;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private DateFormat timeFormat;
    private String date;


    public Message() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_message, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mDatabase.getReference().child("Mixed").child("messages");

        numberEditText = mView.findViewById(R.id.number_editText);
        callTextView = mView.findViewById(R.id.call_textView);
        contactImageButton = mView.findViewById(R.id.contact_button);

        mMessageListView = (ListView) mView.findViewById(R.id.messageListView);
        mMessageEditText = (EditText) mView.findViewById(R.id.messageEditText);
        mSendButton = (Button) mView.findViewById(R.id.sendButton);

        List<MixedMessage> mixedMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(getContext(), R.layout.item_message, mixedMessages);
        mMessageListView.setAdapter(mMessageAdapter);


        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MixedMessage mixedMessage = dataSnapshot.getValue(MixedMessage.class);
                mMessageAdapter.add(mixedMessage);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String mPhone = numberEditText.getText().toString();
                if(mPhone.isEmpty()){
                    Toast.makeText(getContext(), "enter a number please!", Toast.LENGTH_SHORT).show();
                }else {
                    timeFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    date = timeFormat.format(Calendar.getInstance().getTime());
                    MixedMessage mixedMessage = new MixedMessage(mMessageEditText.getText().toString(), mPhone, date);
                    mMessagesDatabaseReference.push().setValue(mixedMessage);
                    manageButton(mPhone);
                    // Clear input box
                    mMessageEditText.setText("");
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
        }
    }

    public void manageButton(String phoneN){
        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, REQUEST_PERMISSSION_SEND_SMS);
            manageButton(phoneN);
        }else {
            sendSMS(phoneN);
        }
    }
    public void sendSMS(String phone){

        String message = mMessageEditText.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message,  null, null );

        Toast.makeText(getContext(),  "SMS sent successfully", Toast.LENGTH_SHORT).show();
    }

}
