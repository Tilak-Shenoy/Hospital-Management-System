package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class pharmaMain extends AppCompatActivity {
    EditText email;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharma_main);
        getSupportActionBar().setTitle("Fortis Pharmacy");
        email = (EditText) findViewById(R.id.email);
    }

    public void onClick(View v) {
        String em = email.getText().toString();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Meds");

        Query query = mFirebaseDatabase.orderByChild("patientEmail").equalTo(em);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    ArrayList arr = new ArrayList<String>();
                    String Pemail="";
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String me = (String) issue.child("patientMedication").getValue();
                         Pemail = (String) issue.child("patientEmail").getValue();
                        arr.add(me);

                    }

                    Intent nextPage;
                    nextPage = new Intent(pharmaMain.this,Pharmacy.class);
                    nextPage.putStringArrayListExtra("arr",arr);
                    nextPage.putExtra("email",Pemail);
                    startActivity(nextPage);

                }
                else{

                    Toast.makeText(getApplicationContext(), "No Medication found for the patient", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
