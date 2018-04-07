package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hospitalmanagement.data.DetailsDbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivilegedAction;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    EditText emailText,passText;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String userId;
    public static String Pname,Pemail,Ppass;
    public static Patient p = new Patient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        DetailsDbHelper db=new DetailsDbHelper(this);
        emailText = (EditText) findViewById(R.id.input_email);
        passText = (EditText) findViewById(R.id.input_password);


        Button sign_in_patient = (Button)findViewById(R.id.btn_signin_patient);
        sign_in_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth();
            }
        });

        TextView sign_up = (TextView)findViewById(R.id.link_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignupActivity.class));
            }
        });

    }
    void Auth () {

        final String email, password;
        email = emailText.getText().toString();
        password = passText.getText().toString();

        Intent nextPage;
        if(  email.equals("pharma@hosp.com") & password.equals("pharma") ){
            nextPage = new Intent(SignInActivity.this, pharmaMain.class);
            startActivity(nextPage);
        }
        else if(  email.equals("admin@hosp.com") & password.equals("admin") ){
            nextPage = new Intent(SignInActivity.this, AdminActivity.class);
            startActivity(nextPage);
        }
        else if( email.equals("drstrange@hosp.com") & password.equals( "marvel")){
            nextPage = new Intent(SignInActivity.this,DoctorActivity.class);
            startActivity(nextPage);
        }
        else{
            mFirebaseInstance = FirebaseDatabase.getInstance();
            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("users");

            Query query = mFirebaseDatabase.orderByChild("patientEmail").equalTo(email);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int b = 0;
                        for (DataSnapshot issue: dataSnapshot.getChildren()) {
                            Pname = (String) issue.child("patientname").getValue();
                            Pemail = (String) issue.child("patientEmail").getValue();
                            Ppass = (String) issue.child("patientPass").getValue();
                            if(password.equals(Ppass)){
                                b = 1;
                                p.setPass(Ppass);
                                p.setPatientEmail(Pemail);
                                p.setPatientname(Pname);
                                Intent nextPage;
                                nextPage = new Intent(SignInActivity.this,PatientActivity.class);
                                nextPage.putExtra("email",Pemail);
                                nextPage.putExtra("name",Pname);
                                startActivity(nextPage);
                                break;
                            }

                        }
                        if(b == 0){
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                        }
                   }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                }
            });

        }



    }

}
