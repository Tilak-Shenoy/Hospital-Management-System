package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ArrayList<String> doctors = new ArrayList<String>();
    Button book;
    EditText name,dept, date,descrip;
    String dName,Dept,desc;
    String Pname,Pemail;
    String appDate;
    private String userId;
    RadioButton r1,r2,r3;
    int Time;
    Spinner spinner;
    Patient p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        getSupportActionBar().setTitle("Fortis Care");

        Pname = getIntent().getExtras().getString("name");
        Pemail = getIntent().getExtras().getString("email");
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        dept=(EditText) findViewById(R.id.dept);
        date=(EditText) findViewById(R.id.date);
        spinner=(Spinner) findViewById(R.id.spinner);
        descrip=(EditText) findViewById(R.id.description);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        
        doctors.add("Dr Stephen Strange");
        doctors.add("Dr Lacy Windham");
        doctors.add("Dr Batra");
        doctors.add("Dr Tom Hardy");
        doctors.add("Dr Bhausingh");
        doctors.add("Dr Srinivas Iyer");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, doctors);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }
    public void onClick(View view) {

        Dept = dept.getText().toString();
        desc = descrip.getText().toString();
        appDate = date.getText().toString();
        Appointment app = new Appointment(dName,Pname,appDate,Time,Dept,desc,Pemail);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        Log.d("email","fhajk");
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");
        userId = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(userId).setValue(app);
        Toast.makeText(this,"Appointment Created!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(BookAppointment.this,patientMain.class);
        i.putExtra("email",Pemail);
        i.putExtra("name",Pname);
        startActivity(i);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dName = doctors.get(i);
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
