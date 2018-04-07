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

/**
 * Created by harsh on 4/4/17.
 */

public class DoctorPrescription extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    EditText Meds,Comment;
    String email="jkks";
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prescription);

        Meds = (EditText) findViewById(R.id.editText);


        Comment = (EditText) findViewById(R.id.editText2);


    }
    public void onClick(View V){
        String meds = Meds.getText().toString();
        String comment = Comment.getText().toString();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Meds");
        userId = mFirebaseDatabase.push().getKey();
        Medication m = new Medication(email,meds,comment);

        mFirebaseDatabase.child(userId).setValue(m);
        /*Intent intent= new Intent(SignupActivity.this,PatientActivity.class);
        //   intent.putExtra("patient",  patient);
        startActivity(intent);*/

    }
}
