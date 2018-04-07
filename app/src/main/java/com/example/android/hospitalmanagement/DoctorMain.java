package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DoctorMain extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    String email,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        Intent i = getIntent();
        email=i.getExtras().getString("email");
        name=i.getExtras().getString("name");
    }
    public void onClickPast(View v){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");



        mFirebaseDatabase.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Appointment> appt = new ArrayList<>();
                    String doctorName;
                    String date;
                    long timeSlot;
                    String description;
                    String dept;
                    String email;
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        date = (String) issue.child("date").getValue();
                        description = (String) issue.child("descrip").getValue();
                        email = (String) issue.child("email").getValue();
                        doctorName = (String) issue.child("name").getValue();
                        timeSlot = (long) issue.child("time").getValue();
                        dept = (String) issue.child("dept").getValue();
                        Date curr = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                        Date cdate = null;
                        try {
                            cdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(cdate.before(curr)){
                            Appointment app = new Appointment(doctorName,date,timeSlot,dept,description,email);
                            Log.d("asd",app.getDescrip());
                            appt.add(app);
                        }

                    }
                    Intent i = new Intent(DoctorMain.this,PatientActivity.class);
                    i.putExtra("Appointments",appt);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("true","false");

            }
        });
    }

    public void onClickPresent(View v){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");

        mFirebaseDatabase.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Appointment> appt = new ArrayList<>();
                    String doctorName;
                    String date;
                    long timeSlot;
                    String description;
                    String dept;
                    String email;
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        date = (String) issue.child("date").getValue();
                        description = (String) issue.child("descrip").getValue();
                        email = (String) issue.child("email").getValue();
                        doctorName = (String) issue.child("name").getValue();
                        timeSlot = (long) issue.child("time").getValue();
                        dept = (String) issue.child("dept").getValue();
                        Date curr = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                        Date cdate = null;

                        try {
                            cdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(cdate.after(curr)){
                            Appointment app = new Appointment(doctorName,date,timeSlot,dept,description,email);
                            Log.d("asd",app.getDescrip());
                            appt.add(app);
                        }

                    }
                    Intent i = new Intent(DoctorMain.this,DoctorActivity.class);
                    i.putExtra("Appointments",appt);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("true","false");

            }
        });
    }

}
