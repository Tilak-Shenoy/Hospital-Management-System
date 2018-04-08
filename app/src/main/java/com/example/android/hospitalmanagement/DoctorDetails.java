package com.example.android.hospitalmanagement;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.PendingIntent.getActivity;

public class DoctorDetails  extends AppCompatActivity {
    Button signUp;
    EditText name, email, pass,dept ;
    String na,em,pa,dp;
    private String userId;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_details);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        dept =(EditText) findViewById(R.id.department);

         mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
         mFirebaseDatabase = mFirebaseInstance.getReference("doctors");

        signUp=(Button) findViewById(R.id.submit);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                na = name.getText().toString();
                em = email.getText().toString();
                pa = pass.getText().toString();
                dp = dept.getText().toString();
                userId = mFirebaseDatabase.push().getKey();
                Doctor doc = new Doctor(na,em,pa,dp,1);
                Log.d("email",doc.getEmail());
                mFirebaseDatabase.child(userId).setValue(doc);
                Intent intent= new Intent(DoctorDetails.this,AdminActivity.class);
                startActivity(intent);

            }
        });

    }
}
