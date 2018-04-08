package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class patientMain extends AppCompatActivity {
    static String name,email;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        Intent i = getIntent();
        email=i.getExtras().getString("email");
        name=i.getExtras().getString("name");
        getSupportActionBar().setTitle("Fortis Care : "+name);

    }
    public void onClickPast(View v){

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");

        mFirebaseDatabase.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Appointment> appt = new ArrayList<>();

                if (dataSnapshot.exists()) {

                    String doctorName,date,description,dept,email;
                    long timeSlot;
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        date = (String) issue.child("date").getValue();
                        description = (String) issue.child("descrip").getValue();
                        email = (String) issue.child("email").getValue();
                        doctorName = (String) issue.child("name").getValue();
                        timeSlot = (long) issue.child("time").getValue();
                        dept = (String) issue.child("dept").getValue();

                        Date curr = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                        String cu = df.format(curr);

                        Date cdate = null;

                        try {
                            curr=df.parse(cu);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            cdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(cdate.before(curr)){
                            Appointment app = new Appointment(doctorName,name,date,timeSlot,dept,description,email);
                            Log.d("asd",app.getDescrip());
                            appt.add(app);
                        }
                    }
                }

                Intent i = new Intent(patientMain.this,PatientActivity.class);
                i.putExtra("Appointments",appt);
                i.putExtra("email",email);
                i.putExtra("state","past");
                i.putExtra("name",name);
                startActivity(i);
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

        mFirebaseDatabase.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Appointment> appt = new ArrayList<>();
                if (dataSnapshot.exists()) {

                    String doctorName,date,description,dept,email;
                    long timeSlot;

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
                        String cu = df.format(curr);

                        try {
                            curr=df.parse(cu);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            cdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(cdate.after(curr)){
                            Appointment app = new Appointment(doctorName,name,date,timeSlot,dept,description,email);
                            Log.d("asd",app.getDescrip());
                            appt.add(app);
                        }
                    }
                }

                Intent i = new Intent(patientMain.this,PatientActivity.class);
                i.putExtra("Appointments",appt);
                i.putExtra("email",email);
                i.putExtra("state","scheduled");
                i.putExtra("name",name);
                startActivity(i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("true","false");

            }
        });
    }

    public void onClick(View v){
        Intent i = new Intent(patientMain.this, BookAppointment.class);
        i.putExtra("email",email);
        i.putExtra("name",name);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(patientMain.this,SignInActivity.class));
    }
}
