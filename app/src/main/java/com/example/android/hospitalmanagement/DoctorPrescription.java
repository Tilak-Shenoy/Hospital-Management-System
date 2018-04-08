package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by harsh on 4/4/17.
 */

public class DoctorPrescription extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    EditText Meds,Comment;
    String email,state;
    ArrayList list;
    private String userId;
    private Appointment app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prescription);
        Meds = (EditText) findViewById(R.id.editText);
        Comment = (EditText) findViewById(R.id.editText2);

        app= (Appointment) getIntent().getSerializableExtra("currentAppt");
        list= (ArrayList) getIntent().getSerializableExtra("Appointments");
        state=getIntent().getExtras().getString("state");

    }
    public void onClick(View V){
        String meds = Meds.getText().toString();
        String comment = Comment.getText().toString();

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'Meds' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Meds");
        userId = mFirebaseDatabase.push().getKey();
        Medication m = new Medication(app.getEmail(),meds,comment,app.getDate());

        mFirebaseDatabase.child(userId).setValue(m);
        Intent intent= new Intent(DoctorPrescription.this,DoctorActivity.class);
        //   intent.putExtra("patient",  patient);
        intent.putExtra("Appointments",list);
        intent.putExtra("state",state);
        Log.d("state",state);
        startActivity(intent);

    }
}
