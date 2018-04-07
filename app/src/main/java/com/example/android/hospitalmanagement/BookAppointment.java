package com.example.android.hospitalmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;

public class BookAppointment extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Button book;
    EditText name,dept, date,descrip;
    String dName,Dept,desc;
    String appDate;
    private String userId;
    RadioButton r1,r2,r3;
    int Time;
    Patient p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        name=(EditText) findViewById(R.id.docName);
        dept=(EditText) findViewById(R.id.dept);
        date=(EditText) findViewById(R.id.date);
       // time=(EditText) findViewById(R.id.time);
        descrip=(EditText) findViewById(R.id.description);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);


        Button submit= (Button) findViewById(R.id.submit);

      }
    public void onClick(View view) {
        dName=name.getText().toString();
        Dept = dept.getText().toString();
        desc = descrip.getText().toString();
        appDate = date.getText().toString();
        Appointment app = new Appointment(dName,appDate,Time,Dept,desc,p.getPatientEmail());
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        Log.d("email","fhajk");
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");
        userId = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(userId).setValue(app);

        /*Query query = mFirebaseDatabase.orderByChild("patientEmail").equalTo(p.getPatientEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Log.d("email","fhajk");
                        userId = issue.getKey();
                        mFirebaseDatabase.child(userId).child("patientEmail").setValue("Yay is works");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    Time = 4;
                break;
            case R.id.radioButton2:
                if (checked)
                    Time = 5;
                break;
            case R.id.radioButton3:
                if (checked)
                    Time = 6;
                break;
        }
    }}
