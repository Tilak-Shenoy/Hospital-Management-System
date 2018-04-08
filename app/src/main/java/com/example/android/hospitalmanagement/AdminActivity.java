package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by harsh on 4/4/17.
 */

public class AdminActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
    }
    public void onClickRecords(View v){

        Intent nextPage;
        nextPage = new Intent(AdminActivity.this,Records.class);
        startActivity(nextPage);

    }
    public void onClickAddDoc(View v){
        Intent nextPage;
        nextPage = new Intent(AdminActivity.this,DoctorDetails.class);
        startActivity(nextPage);

    }

}
