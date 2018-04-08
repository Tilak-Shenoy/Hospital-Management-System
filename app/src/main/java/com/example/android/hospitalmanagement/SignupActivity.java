package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class SignupActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Button signUp;
    EditText name, email, pass;
    String na,em,pa;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = (Button) findViewById(R.id.signUp);
        name = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                na = name.getText().toString();
                em = email.getText().toString();
                pa = pass.getText().toString();
                userId = mFirebaseDatabase.push().getKey();
                Patient patient = new Patient(na,em,pa);
                Log.d("email",patient.getPatientEmail());
                mFirebaseDatabase.child(userId).setValue(patient);
                Intent intent= new Intent(SignupActivity.this,patientMain.class);
                intent.putExtra("name",na);
                intent.putExtra("email",em);
                startActivity(intent);
            }
        });
    }
}
